package com.hc.cart.controller;

import com.hc.cart.service.CartService;
import com.hc.param.CartListParam;
import com.hc.param.CartSaveParam;
import com.hc.pojo.Cart;
import com.hc.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 何超
 * @date: 2022/11/15
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping("/save")
    public R save(@RequestBody @Validated CartSaveParam cartSaveParam, BindingResult result) {
        if (result.hasErrors()) {
            return R.fail("参数错误，添加失败！");
        }
        return cartService.save(cartSaveParam);
    }

    @PostMapping("/list")
    public R list(@RequestBody @Validated CartListParam cartListParam, BindingResult result) {
        if (result.hasErrors()) {
            return R.fail("购物车数据查询失败！");
        }
        return cartService.list(cartListParam.getUserId());
    }

    @PostMapping("/update")
    public R update(@RequestBody @Validated Cart cart) {
        return cartService.update(cart);
    }

    @PostMapping("remove")
    public R remove(@RequestBody Cart cart) {

        return cartService.remove(cart);
    }

    @PostMapping("/remove/check")
    public R removeCheck(@RequestBody Integer productId) {
        return cartService.check(productId);
    }

}
