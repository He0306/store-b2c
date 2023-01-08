package com.hc.cart.service;

import com.hc.param.CartSaveParam;
import com.hc.pojo.Cart;
import com.hc.utils.R;

import java.util.List;

/**
 * @author: 何超
 * @date: 2022/11/15
 */
public interface CartService {

    /**
     * 购物车数据添加
     *
     * @param cartSaveParam
     * @return
     */
    R save(CartSaveParam cartSaveParam);

    /**
     * 返回购物车数据
     *
     * @param userId
     * @return
     */
    R list(Integer userId);

    /**
     * 更新购物车
     *
     * @param cart
     * @return
     */
    R update(Cart cart);

    /**
     * 移除购物车数据
     *
     * @param cart
     * @return
     */
    R remove(Cart cart);

    /**
     * 清空对应ID的购物车项
     *
     * @param cartIds
     */
    void clearIds(List<Integer> cartIds);

    /**
     * 查询购物车项
     *
     * @param productId
     * @return
     */
    R check(Integer productId);
}
