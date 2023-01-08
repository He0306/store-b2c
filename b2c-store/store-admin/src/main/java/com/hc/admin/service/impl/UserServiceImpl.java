package com.hc.admin.service.impl;

import com.hc.admin.service.UserService;
import com.hc.clients.UserClient;
import com.hc.param.CartListParam;
import com.hc.param.PageParam;
import com.hc.pojo.User;
import com.hc.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author: 何超
 * @date: 2022/11/17
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserClient userClient;

    @Cacheable(value = "list.user", key = "#pageParam.currentPage + '-' + #pageParam.pageSize")
    @Override
    public R userList(PageParam pageParam) {
        return userClient.listPage(pageParam);
    }

    @CacheEvict(value = "list.user", allEntries = true)
    @Override
    public R remove(CartListParam cartListParam) {
        return userClient.remove(cartListParam);
    }

    @CacheEvict(value = "list.user", allEntries = true)
    @Override
    public R update(User user) {
        return userClient.update(user);
    }

    @CacheEvict(value = "list.user", allEntries = true)
    @Override
    public R save(User user) {
        return userClient.save(user);
    }
}
