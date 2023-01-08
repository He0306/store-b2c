package com.hc.admin.service;

import com.hc.admin.param.AdminParam;
import com.hc.admin.pojo.AdminUser;

/**
 * @author: 何超
 * @date: 2022/11/17
 */
public interface AdminUserService {

    /**
     * 登录
     *
     * @param adminParam
     * @return
     */
    AdminUser login(AdminParam adminParam);
}
