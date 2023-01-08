package com.hc.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hc.pojo.Product;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: 何超
 * @date: 2022/11/14
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {
}
