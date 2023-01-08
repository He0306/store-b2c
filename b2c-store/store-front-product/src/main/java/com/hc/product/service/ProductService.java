package com.hc.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hc.param.*;
import com.hc.pojo.Product;
import com.hc.to.OrderToProduct;
import com.hc.utils.R;

import java.util.List;

/**
 * @author: 何超
 * @date: 2022/11/14
 */
public interface ProductService extends IService<Product> {

    /**
     * 单类别名称 查询热门商品 最多7条
     *
     * @param categoryName
     * @return
     */
    R promo(String categoryName);

    /**
     * 多类别热门商品查询
     *
     * @param productHotParam
     * @return
     */
    R hots(ProductHotParam productHotParam);

    /**
     * 查询类别商品集合
     *
     * @return
     */
    R clist();

    /**
     * 通用型业务
     * 传入了类型ID，根据ID查询并分页
     * 没有传入类别ID，查询全部
     *
     * @param productIdsParam
     * @return
     */
    R byCategory(ProductIdsParam productIdsParam);

    /**
     * 根据商品ID查询商品详情信息
     *
     * @param productID
     * @return
     */
    R detail(Integer productID);

    /**
     * 查询商品对应图片集合
     *
     * @param productID
     * @return
     */
    R pictures(Integer productID);

    /**
     * 搜索服务调用，获取全部商品服务
     *
     * @return
     */
    List<Product> allList();

    /**
     * 搜索业务
     *
     * @param productSearchParam
     * @return
     */
    R search(ProductSearchParam productSearchParam);

    /**
     * 根据商品ID查询商品信息
     *
     * @param ids
     * @return
     */
    R ids(List<Integer> ids);

    /**
     * 根据商品ID查询商品集合
     *
     * @param ids
     * @return
     */
    List<Product> cartList(List<Integer> ids);

    /**
     * 修改库存和增加销售量
     *
     * @param orderToProducts
     */
    void subNumber(List<OrderToProduct> orderToProducts);

    /**
     * 类别对应的商品数量查询
     *
     * @param categoryId
     * @return
     */
    Long adminCount(Integer categoryId);

    /**
     * 查询所有商品
     *
     * @param pageParam
     * @return
     */
    R listPage(PageParam pageParam);

    /**
     * 商品保存业务
     *
     * @param productSaveParam
     * @return
     */
    R adminSave(ProductSaveParam productSaveParam);

    /**
     * 商品更新
     *
     * @param product
     * @return
     */
    R adminUpdate(Product product);

    /**
     * 商品删除业务
     *
     * @param productId
     * @return
     */
    R adminRemove(Integer productId);
}
