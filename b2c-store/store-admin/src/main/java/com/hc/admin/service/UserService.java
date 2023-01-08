package com.hc.admin.service;

import com.hc.param.CartListParam;
import com.hc.param.PageParam;
import com.hc.pojo.User;
import com.hc.utils.R;

/**
 * @author: 何超
 * @date: 2022/11/17
 */
public interface UserService {

    /**
     * 用户信息展示
     *
     * @param pageParam
     * @return
     */
    R userList(PageParam pageParam);

    /**
     * 根据ID删除用户数据
     *
     * @param cartListParam
     * @return
     */
    R remove(CartListParam cartListParam);

    /**
     * 修改用户信息
     *
     * @param user
     * @return
     */
    R update(User user);

    /**
     * 新增用户信息
     *
     * @param user
     * @return
     */
    R save(User user);
}
