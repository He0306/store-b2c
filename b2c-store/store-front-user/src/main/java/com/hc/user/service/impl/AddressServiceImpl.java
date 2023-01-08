package com.hc.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hc.pojo.Address;
import com.hc.user.mapper.AddressMapper;
import com.hc.user.service.AddressService;
import com.hc.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: 何超
 * @date: 2022/11/14
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressMapper addressMapper;

    @Override
    public R list(Integer userId) {
        QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<Address> addressList = addressMapper.selectList(queryWrapper);
        return R.ok("查询成功", addressList);
    }

    @Override
    public R save(Address address) {
        int rows = addressMapper.insert(address);
        if (rows == 0) {
            return R.fail("插入地址失败！");
        }
        return list(address.getUserId());
    }

    @Override
    public R remove(Integer userId) {
        int rows = addressMapper.deleteById(userId);
        if (rows == 0) {
            return R.fail("删除地址失败！");
        }
        return R.ok("地址删除成功！");
    }
}
