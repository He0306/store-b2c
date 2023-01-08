package com.hc.cart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hc.cart.mapper.CartMapper;
import com.hc.cart.service.CartService;
import com.hc.clients.ProductClient;
import com.hc.param.CartSaveParam;
import com.hc.param.ProductCollectParam;
import com.hc.param.ProductIdParam;
import com.hc.pojo.Cart;
import com.hc.pojo.Product;
import com.hc.utils.R;
import com.hc.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: 何超
 * @date: 2022/11/15
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartMapper cartMapper;

    @Autowired
    ProductClient productClient;

    @Override
    public R save(CartSaveParam cartSaveParam) {
        //查询商品数据
        ProductIdParam productIdParam = new ProductIdParam();
        productIdParam.setProductID(cartSaveParam.getProductId());
        Product product = productClient.cdetail(productIdParam);
        if (product == null) {
            return R.fail("商品已经被删除，无法添加到！");
        }
        //检查库存
        if (product.getProductNum() == 0) {
            R ok = R.ok("没有库存数据！无法购买");
            ok.setCode("003");
            return ok;
        }
        //检查是否添加过
        LambdaQueryWrapper<Cart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cart::getUserId, cartSaveParam.getUserId());
        queryWrapper.eq(Cart::getProductId, cartSaveParam.getProductId());
        Cart cart = cartMapper.selectOne(queryWrapper);
        if (cart != null) {
            cart.setNum(cart.getNum() + 1);
            cartMapper.updateById(cart);
            R ok = R.ok("购物车存在该商品，数量+1");
            ok.setCode("002");
            return ok;
        }
        //添加购物车
        cart = new Cart();
        cart.setNum(1);
        cart.setUserId(cartSaveParam.getUserId());
        cart.setProductId(cartSaveParam.getProductId());
        cartMapper.insert(cart);
        //返回数据
        CartVo cartVo = new CartVo(product, cart);
        return R.ok("购物车数据添加成功！", cartVo);
    }

    @Override
    public R list(Integer userId) {
        LambdaQueryWrapper<Cart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cart::getUserId, userId);
        List<Cart> carts = cartMapper.selectList(queryWrapper);
        if (carts == null || carts.size() == 0) {
            carts = new ArrayList<>();
            return R.ok("购物车空空如也！", carts);
        }
        // 获取商品ID集合，调用商品服务查询
        List<Integer> productIds = new ArrayList<>();
        for (Cart cart : carts) {
            productIds.add(cart.getProductId());
        }
        ProductCollectParam productCollectParam = new ProductCollectParam();
        productCollectParam.setIds(productIds);
        List<Product> productList = productClient.cartList(productCollectParam);
        Map<Integer, Product> collect = productList.stream().collect(Collectors.toMap(Product::getProductId, v -> v));
        List<CartVo> cartVoList = new ArrayList<>();
        for (Cart cart : carts) {
            CartVo cartVo = new CartVo(collect.get(cart.getProductId()), cart);
            cartVoList.add(cartVo);
        }
        return R.ok("数据查询成功！", cartVoList);
    }

    /**
     * 跟新购物车业务
     * 1、更新商品数据
     * 2、判断库存是否可用
     * 3、正常修改即可
     *
     * @param cart
     * @return
     */
    @Override
    public R update(Cart cart) {
        ProductIdParam productIdParam = new ProductIdParam();
        productIdParam.setProductID(cart.getProductId());
        Product product = productClient.cdetail(productIdParam);
        //判断库存
        if (cart.getNum() > product.getProductNum()) {
            return R.fail("修改失败！库存不足！");
        }
        //修改数据
        LambdaQueryWrapper<Cart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cart::getUserId, cart.getUserId());
        queryWrapper.eq(Cart::getProductId, cart.getProductId());
        Cart newCart = cartMapper.selectOne(queryWrapper);
        newCart.setNum(cart.getNum());
        cartMapper.updateById(newCart);
        return R.ok("修改购物车成功！");
    }

    @Override
    public R remove(Cart cart) {
        LambdaQueryWrapper<Cart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cart::getUserId, cart.getUserId());
        queryWrapper.eq(Cart::getProductId, cart.getProductId());
        cartMapper.delete(queryWrapper);
        return R.ok("删除数据成功！");
    }

    @Override
    public void clearIds(List<Integer> cartIds) {
        cartMapper.deleteBatchIds(cartIds);
    }

    /**
     * 查询购物车项
     *
     * @param productId
     * @return
     */
    @Override
    public R check(Integer productId) {
        LambdaQueryWrapper<Cart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cart::getProductId, productId);
        Long count = cartMapper.selectCount(queryWrapper);
        if (count > 0) {
            return R.fail("有" + count + "将购物车商品引用|删除失败！");
        }
        return R.ok("购物车无商品引用！");
    }
}
