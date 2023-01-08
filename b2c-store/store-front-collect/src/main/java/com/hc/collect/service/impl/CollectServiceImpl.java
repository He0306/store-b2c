package com.hc.collect.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hc.clients.ProductClient;
import com.hc.collect.mapper.CollectMapper;
import com.hc.collect.service.CollectService;
import com.hc.param.ProductCollectParam;
import com.hc.pojo.Collect;
import com.hc.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 何超
 * @date: 2022/11/15
 */
@Service
public class CollectServiceImpl implements CollectService {

    @Autowired
    CollectMapper collectMapper;

    @Autowired
    ProductClient productClient;

    @Override
    public R save(Collect collect) {
        LambdaQueryWrapper<Collect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Collect::getUserId, collect.getUserId());
        queryWrapper.eq(Collect::getProductId, collect.getProductId());
        Long count = collectMapper.selectCount(queryWrapper);
        if (count > 0) {
            return R.fail("收藏已经添加，无需二次添加！");
        }
        collect.setCollectTime(System.currentTimeMillis());
        collectMapper.insert(collect);

        return R.ok("收藏成功！");
    }

    @Override
    public R list(Integer userId) {
        LambdaQueryWrapper<Collect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Collect::getUserId, userId);
        queryWrapper.select(Collect::getProductId);
        List<Object> idsObject = collectMapper.selectObjs(queryWrapper);
        ProductCollectParam productCollectParam = new ProductCollectParam();
        List<Integer> ids = new ArrayList<>();
        for (Object o : idsObject) {
            ids.add((Integer) o);
        }
        productCollectParam.setIds(ids);
        return productClient.productIds(productCollectParam);
    }

    @Override
    public R remove(Collect collect) {
        LambdaQueryWrapper<Collect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Collect::getUserId, collect.getUserId());
        queryWrapper.eq(Collect::getProductId, collect.getProductId());
        int rows = collectMapper.delete(queryWrapper);
        if (rows == 0) {
            return R.fail("收藏移除失败！");
        }
        return R.ok("收藏移除成功！");
    }

    /**
     * 删除根据商品ID
     *
     * @param productId
     * @return
     */
    @Override
    public R removeById(Integer productId) {
        LambdaQueryWrapper<Collect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Collect::getProductId, productId);
        int rows = collectMapper.delete(queryWrapper);
        return R.ok("收藏商品删除成功！");
    }
}
