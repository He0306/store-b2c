package com.hc.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hc.admin.mapper.AdminUserMapper;
import com.hc.admin.param.AdminParam;
import com.hc.admin.pojo.AdminUser;
import com.hc.admin.service.AdminUserService;
import com.hc.constants.UserConstants;
import com.hc.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: 何超
 * @date: 2022/11/17
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    AdminUserMapper adminUserMapper;

    @Override
    public AdminUser login(AdminParam adminParam) {
        LambdaQueryWrapper<AdminUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AdminUser::getUserAccount, adminParam.getUserAccount());
        queryWrapper.eq(AdminUser::getUserPassword, MD5Util.encode(adminParam.getUserPassword() + UserConstants.USER_SLAT));
        return adminUserMapper.selectOne(queryWrapper);
    }
}
