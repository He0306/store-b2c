package com.hc.user.service;

import com.hc.param.PageParam;
import com.hc.param.UserCheckParam;
import com.hc.param.UserLoginParam;
import com.hc.pojo.User;
import com.hc.utils.R;

/**
 * @author: 何超
 * @date: 2022/11/13
 */
public interface UserService {

    /**
     * 检查账号是否可用业务
     *
     * @param userCheckParam
     * @return
     */
    R check(UserCheckParam userCheckParam);

    /**
     * 注册
     *
     * @param user
     * @return
     */
    R regsiter(User user);

    /**
     * 登录
     *
     * @param userLoginParam
     * @return
     */
    R login(UserLoginParam userLoginParam);

    /**
     * 后台管理调用查询所有用户数据
     *
     * @param pageParam
     * @return
     */
    R listPage(PageParam pageParam);

    /**
     * 根据用户ID删除数据
     *
     * @param userId
     * @return
     */
    R remove(Integer userId);

    /**
     * 修改用户数据
     *
     * @param user
     * @return
     */
    R update(User user);

    /**
     * 新增用户数据
     *
     * @param user
     * @return
     */
    R save(User user);
}
