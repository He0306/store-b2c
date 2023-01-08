package com.hc.collect.service;

import com.hc.pojo.Collect;
import com.hc.utils.R;

/**
 * @author: 何超
 * @date: 2022/11/15
 */
public interface CollectService {

    /**
     * 收藏添加的方法
     *
     * @param collect
     * @return
     */
    R save(Collect collect);

    /**
     * 根据用户ID查询对应商品
     *
     * @param userId
     * @return
     */
    R list(Integer userId);

    /**
     * 根据用户ID查询对应商品
     *
     * @param collect
     * @return
     */
    R remove(Collect collect);

    /**
     * 删除根据商品ID
     *
     * @param productId
     * @return
     */
    R removeById(Integer productId);
}
