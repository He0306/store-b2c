package com.hc.user.service;

import com.hc.pojo.Address;
import com.hc.utils.R;

/**
 * @author: 何超
 * @date: 2022/11/14
 */
public interface AddressService {

    /**
     * 根据用户ID查询地址
     *
     * @param userId
     * @return
     */
    R list(Integer userId);

    /**
     * 添加地址
     *
     * @param address
     * @return
     */
    R save(Address address);

    /**
     * 根据ID 删除地址信息
     *
     * @param userId
     * @return
     */
    R remove(Integer userId);
}
