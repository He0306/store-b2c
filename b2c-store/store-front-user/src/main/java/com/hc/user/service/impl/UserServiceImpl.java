package com.hc.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hc.constants.UserConstants;
import com.hc.param.PageParam;
import com.hc.param.UserCheckParam;
import com.hc.param.UserLoginParam;
import com.hc.pojo.User;
import com.hc.user.mapper.UserMapper;
import com.hc.user.service.UserService;
import com.hc.utils.MD5Util;
import com.hc.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: 何超
 * @date: 2022/11/13
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public R check(UserCheckParam userCheckParam) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userCheckParam.getUserName());
        Long total = userMapper.selectCount(queryWrapper);
        if (total == 0) {
            return R.ok("账号不存在，可用使用！");
        }
        return R.fail("账号已存在，不可注册！");
    }

    @Override
    public R regsiter(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", user.getUserName());
        Long total = userMapper.selectCount(queryWrapper);
        if (total > 0) {
            return R.fail("账号已存在，不可注册！");
        }
        String newPwd = MD5Util.encode(user.getPassword() + UserConstants.USER_SLAT);
        user.setPassword(newPwd);
        int rows = userMapper.insert(user);
        if (rows == 0) {
            return R.fail("注册失败！请稍后在试");
        }
        return R.ok("注册成功");
    }

    @Override
    public R login(UserLoginParam userLoginParam) {
        String newPwd = MD5Util.encode(userLoginParam.getPassword() + UserConstants.USER_SLAT);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userLoginParam.getUserName());
        queryWrapper.eq("password", newPwd);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            return R.fail("账号或密码错误！");
        }
        return R.ok("登录成功！", user);

    }

    @Override
    public R listPage(PageParam pageParam) {
        IPage<User> page = new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());
        page = userMapper.selectPage(page, null);
        return R.ok("用户管理查询成功！", page.getRecords(), page.getTotal());
    }

    @Override
    public R remove(Integer userId) {
        userMapper.deleteById(userId);
        return R.ok("用户数据删除成功！");
    }

    @Override
    public R update(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserId, user.getUserId());
        queryWrapper.eq(User::getPassword, user.getPassword());
        Long count = userMapper.selectCount(queryWrapper);
        if (count == 0) {
            user.setPassword(MD5Util.encode(user.getPassword() + UserConstants.USER_SLAT));
        }
        userMapper.updateById(user);
        return R.ok("用户信息修改成功！");
    }

    @Override
    public R save(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", user.getUserName());
        Long total = userMapper.selectCount(queryWrapper);
        if (total > 0) {
            return R.fail("账号已存在，不可注册！");
        }
        String newPwd = MD5Util.encode(user.getPassword() + UserConstants.USER_SLAT);
        user.setPassword(newPwd);
        int rows = userMapper.insert(user);
        if (rows == 0) {
            return R.fail("添加用户失败！请稍后在试");
        }
        return R.ok("添加用户成功！");
    }
}
