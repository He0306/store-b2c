package com.hc.pay.controller;

import cn.hutool.json.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.hc.param.OrderParam;
import com.hc.pay.config.AliPayConfig;
import com.hc.pay.mapper.OrderMapper;
import com.hc.pojo.Order;
import com.hc.to.OrderToProduct;
import com.hc.utils.R;
import com.hc.vo.CartVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 何超
 * @date: 2022/12/16
 */
@RestController
@RequestMapping("/alipay")
@Slf4j
public class PayController {

    private static final String GATEWAY_URL = "https://openapi.alipaydev.com/gateway.do";
    private static final String FORMAT = "JSON";
    private static final String CHARSET = "UTF-8";
    //签名方式
    private static final String SIGN_TYPE = "RSA2";

    private static final long NOW = System.currentTimeMillis();

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    AliPayConfig aliPayConfig;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @PostMapping("/pay")
    public R pay(@RequestBody OrderParam orderParam) throws IOException {
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL,aliPayConfig.getAppId(),aliPayConfig.getAppPrivateKey(),
                FORMAT,CHARSET,aliPayConfig.getAlipayPublicKey(),SIGN_TYPE);
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setNotifyUrl(aliPayConfig.getNotifyUrl());
        List<Integer> cartIds = new ArrayList<>();
        List<OrderToProduct> orderToProducts = new ArrayList<>();
        JSONObject bizContent = new JSONObject();
        for (CartVo cartVo : orderParam.getProducts()) {
            cartIds.add(cartVo.getId());
            OrderToProduct orderToProduct = new OrderToProduct();
            orderToProduct.setProductId(cartVo.getProductID());
            orderToProduct.setNum(cartVo.getNum());
            orderToProducts.add(orderToProduct);
            redisTemplate.opsForValue().set("cartIds", String.valueOf(cartVo.getId()));
            redisTemplate.opsForValue().set("orderToProduct", String.valueOf(orderToProduct));
            bizContent.set("cartIds",cartIds);
            bizContent.set("orderToProducts",orderToProducts);
            bizContent.set("user_id",orderParam.getUserId());
            bizContent.set("product_id",cartVo.getProductID());
            bizContent.set("product_num",cartVo.getNum());
            bizContent.set("out_trade_no", NOW);  // 我们自己生成的订单编号
            bizContent.set("total_amount", cartVo.getPrice()); // 订单的总金额
            bizContent.set("subject", cartVo.getProductName());   // 支付的名称
            bizContent.set("product_code", "FAST_INSTANT_TRADE_PAY");  // 固定配置
            request.setBizContent(bizContent.toString());
            //构建对象
            Order order = new Order();
            order.setOrderId(NOW);
            order.setUserId(orderParam.getUserId());
            order.setProductId(cartVo.getProductID());
            order.setProductNum(cartVo.getNum());
            order.setProductPrice(cartVo.getPrice());
            order.setProductName(cartVo.getProductName());
            order.setState("未支付");
            //保存
            orderMapper.insert(order);
        }
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return R.ok("调用成功！",form);
    }

    @PostMapping("/notify")  // 注意这里必须是POST接口
    public String payNotify(HttpServletRequest request) throws Exception {
        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {

            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
                // System.out.println(name + " = " + request.getParameter(name));
            }
            System.out.println("=====>"+params);
            Long tradeNo = Long.valueOf(params.get("out_trade_no"));
            String gmtPayment = params.get("gmt_payment");
            String alipayTradeNo = params.get("trade_no");

            String sign = params.get("sign");
            String content = AlipaySignature.getSignCheckContentV1(params);
            boolean checkSignature = AlipaySignature.rsa256CheckContent(content, sign, aliPayConfig.getAlipayPublicKey(), "UTF-8"); // 验证签名
            // 支付宝验签
            if (checkSignature) {
                // 验签通过
                System.out.println("交易名称: " + params.get("subject"));
                System.out.println("交易状态: " + params.get("trade_status"));
                System.out.println("支付宝交易凭证号: " + params.get("trade_no"));
                System.out.println("商户订单号: " + params.get("out_trade_no"));
                System.out.println("交易金额: " + params.get("total_amount"));
                System.out.println("买家在支付宝唯一id: " + params.get("buyer_id"));
                System.out.println("买家付款时间: " + params.get("gmt_payment"));
                System.out.println("买家付款金额: " + params.get("buyer_pay_amount"));

                orderMapper.updateState(tradeNo,"已支付",gmtPayment, alipayTradeNo);
                Integer cartIds = Integer.valueOf(redisTemplate.opsForValue().get("cartIds"));
                List<Integer> cartIdsList = new ArrayList<>();
                cartIdsList.add(cartIds);

                //发送购物车消息
                rabbitTemplate.convertAndSend("topic.ex", "clear.cart", cartIdsList);
                //发送商品服务消息
                rabbitTemplate.convertAndSend("topic.ex", "sub.number", redisTemplate.opsForValue().get("orderToProduct"));
            }
        }
        return "success";
    }
}
