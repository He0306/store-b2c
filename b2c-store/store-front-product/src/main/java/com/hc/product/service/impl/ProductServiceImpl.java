package com.hc.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hc.clients.*;
import com.hc.param.*;
import com.hc.pojo.Picture;
import com.hc.pojo.Product;
import com.hc.product.mapper.PictureMapper;
import com.hc.product.mapper.ProductMapper;
import com.hc.product.service.ProductService;
import com.hc.to.OrderToProduct;
import com.hc.utils.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: 何超
 * @date: 2022/11/14
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    CategoryClient categoryClient;

    @Autowired
    OrderClient orderClient;

    @Autowired
    CartClient cartClient;

    @Autowired
    CollectClient collectClient;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    PictureMapper pictureMapper;

    @Autowired
    SearchClient searchClient;

    @Cacheable(value = "list.product", key = "#categoryName")
    @Override
    public R promo(String categoryName) {
        R r = categoryClient.byName(categoryName);
        if (r.getCode().equals(R.FAIL_CODE)) {
            return r;
        }
        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) r.getData();
        Integer categoryId = (Integer) map.get("category_id");

        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Product::getCategoryId, categoryId);
        queryWrapper.orderByDesc(Product::getProductSales);
        IPage<Product> page = new Page<>(1, 7);
        IPage<Product> selectPage = productMapper.selectPage(page, queryWrapper);
        List<Product> productList = selectPage.getRecords();
        return R.ok("查询成功！", productList);
    }

    @Cacheable(value = "list.product", key = "#productHotParam")
    @Override
    public R hots(ProductHotParam productHotParam) {

        R r = categoryClient.hotsCategory(productHotParam);
        if (r.getCode().equals(R.FAIL_CODE)) {
            return r;
        }
        List<Object> ids = (List<Object>) r.getData();

        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Product::getCategoryId, ids);
        queryWrapper.orderByDesc(Product::getProductSales);

        IPage<Product> page = new Page<>(1, 7);
        IPage<Product> selectPage = productMapper.selectPage(page, queryWrapper);
        List<Product> records = selectPage.getRecords();
        return R.ok("查询成功！", records);
    }

    @Cacheable(value = "list.category", key = "#root.methodName")
    @Override
    public R clist() {
        return categoryClient.list();
    }

    @Cacheable(value = "productHotParam", key = "#productIdsParam.categoryID + " +
            "'-' + #productIdsParam.currentPage + " +
            "'-' + #productIdsParam.pageSize")
    @Override
    public R byCategory(ProductIdsParam productIdsParam) {
        List<Integer> categoryID = productIdsParam.getCategoryID();
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        if (!categoryID.isEmpty()) {
            queryWrapper.in(Product::getCategoryId, categoryID);
        }
        IPage<Product> page = new Page<>(productIdsParam.getCurrentPage(), productIdsParam.getPageSize());
        page = productMapper.selectPage(page, queryWrapper);
        return R.ok("查询成功！", page.getRecords(), page.getTotal());
    }

    @Cacheable(value = "product", key = "#productID")
    @Override
    public R detail(Integer productID) {
        Product product = productMapper.selectById(productID);
        return R.ok("查询成功！", product);
    }

    @Cacheable(value = "picture", key = "#productID")
    @Override
    public R pictures(Integer productID) {
        LambdaQueryWrapper<Picture> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Picture::getProductId, productID);
        List<Picture> pictureList = pictureMapper.selectList(queryWrapper);
        return R.ok("查询成功！", pictureList);
    }

    @Override
    public List<Product> allList() {
        return productMapper.selectList(null);
    }

    @Override
    public R search(ProductSearchParam productSearchParam) {
        return searchClient.searchProduct(productSearchParam);
    }

    @Cacheable(value = "list.product", key = "#ids")
    @Override
    public R ids(List<Integer> ids) {
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Product::getProductId, ids);
        List<Product> productList = productMapper.selectList(queryWrapper);
        return R.ok("类别信息查询成功！", productList);
    }

    @Override
    public List<Product> cartList(List<Integer> ids) {
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Product::getProductId, ids);
        return productMapper.selectList(queryWrapper);
    }

    /**
     * 修改库存和增加销售量
     *
     * @param orderToProducts
     */
    @Override
    public void subNumber(List<OrderToProduct> orderToProducts) {
        //将1集合转成map
        Map<Integer, OrderToProduct> map = orderToProducts.stream().collect(Collectors.toMap(OrderToProduct::getProductId, v -> v));
        //获取商品的ID集合
        Set<Integer> productIds = map.keySet();
        //查询集合对应的商品信息
        List<Product> productList = productMapper.selectBatchIds(productIds);
        //修改商品信息
        for (Product product : productList) {
            Integer num = map.get(product.getProductId()).getNum();
            product.setProductNum(product.getProductNum() - num);  //减库存
            product.setProductSales(product.getProductSales() + num);  //添加销售量
        }
        //批量更新
        updateBatchById(productList);
    }

    @Override
    public Long adminCount(Integer categoryId) {
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Product::getCategoryId, categoryId);
        return productMapper.selectCount(queryWrapper);
    }

    @Override
    public R listPage(PageParam pageParam) {
        IPage<Product> page = new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());
        page = productMapper.selectPage(page, null);
        return R.ok("查询成功！", page.getRecords(), page.getTotal());
    }

    /**
     * 商品保存业务
     * 1、商品数据保存
     * 2、商品的图片详情切割和保存
     * 3、搜索数据库的数据添加
     * 4、清空商品相关的缓存数据
     *
     * @param productSaveParam
     * @return
     */
    @CacheEvict(value = "list.product", allEntries = true)
    @Override
    public R adminSave(ProductSaveParam productSaveParam) {
        Product product = new Product();
        BeanUtils.copyProperties(productSaveParam, product);

        int rows = productMapper.insert(product);

        String pictures = productSaveParam.getPictures();
        if (!StringUtils.isEmpty(pictures)) {
            String[] urls = pictures.split("[-]");
            for (String url : urls) {
                Picture picture = new Picture();
                picture.setProductId(picture.getProductId());
                picture.setProductPicture(url);
                pictureMapper.insert(picture);
            }
        }
        //同步搜索服务的数据
        searchClient.saveProduct(product);
        return R.ok("商品数据保存成功！");
    }

    /**
     * 商品更新
     * 1、更新商品数据
     * 2、同步搜索服务数据即可
     *
     * @param product
     * @return
     */
    @Override
    public R adminUpdate(Product product) {
        productMapper.updateById(product);
        searchClient.saveProduct(product);
        return R.ok("商品更新成功！");
    }

    /**
     * 商品删除业务
     * 1、检查购物车
     * 2、检查订单
     * 3、删除商品
     * 4、删除商品相关的图片
     * 5、删除收藏
     * 6、进行es数据同步
     *
     * @param productId
     * @return
     */
    @Caching(
            evict = {@CacheEvict(value = "product.list", allEntries = true),
                    @CacheEvict(value = "product", key = "#productId")}
    )
    @Override
    public R adminRemove(Integer productId) {
        R r = cartClient.removeCheck(productId);
        if ("004".equals(r.getCode())) {
            return r;
        }
        r = orderClient.removeCheck(productId);
        if ("004".equals(r.getCode())) {
            return r;
        }
        //删除商品
        productMapper.deleteById(productId);
        //删除商品图片
        LambdaQueryWrapper<Picture> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Picture::getProductId, productId);
        pictureMapper.delete(queryWrapper);
        //删除收藏中和本商品有关的
        collectClient.removeCheck(productId);
        //同步数据
        searchClient.removeProduct(productId);
        return R.ok("商品删除成功！");
    }
}
