package com.hc.admin.controller;

/**
 * @author: 何超
 * @date: 2022/11/17
 */

import com.hc.clients.CategoryClient;
import com.hc.pojo.Category;
import com.hc.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * projectName: b2c-cloud-store
 *
 * @author: 赵伟风
 * description:  专门用于存储,页面跳转handler方法的controller
 */
@Slf4j
@Controller
@RequestMapping
public class HtmlJumpController {

    @Autowired
    CategoryClient categoryClient;

    /**
     * 设计欢迎页面跳转controller
     *
     * @return login 登录页面
     */
    @GetMapping({"/", "index.html", "index"})
    public String welcome() {
        log.info("HtmlJumpController.welcome 跳转登录页面!");
        return "login";
    }


    /**
     * 登录成功跳转到index页面!
     *
     * @return
     */
    @GetMapping("/home")
    public String home() {
        log.info("HtmlJumpController.home登录成功,跳转程序首页!index页面!");
        return "index";
    }

    /**
     * 跳转用户管理页面
     */
    @GetMapping("/user")
    public String user() {
        log.info("HtmlJumpController.user,跳转用户管理!user页面!");
        return "user/user";
    }

    /**
     * 跳转商品管理页面
     */
    @GetMapping("/product")
    public String product() {
        log.info("HtmlJumpController.product,跳转商品管理!product页面!");
        return "product/product";
    }


    /**
     * 跳转类别管理页面
     */
    @GetMapping("/category")
    public String category() {
        log.info("HtmlJumpController.category,跳转用户管理!category页面!");
        return "category/category";
    }


    /**
     * 跳转订单管理页面
     */
    @GetMapping("/order")
    public String order() {
        log.info("HtmlJumpController.order,跳转用户管理!order页面!");
        return "order/order";
    }

    /**
     * 打开编辑用户页面
     */
    @GetMapping("/user/update/html")
    public String userUpdateHtml() {
        log.info("HtmlJumpController.userUpdateHtml业务结束，结果:{}");
        return "user/edit";
    }


    /**
     * 打开编辑用户页面
     */
    @GetMapping("/user/save/html")
    public String userSaveHtml() {
        log.info("HtmlJumpController.userSaveHtml业务结束，结果:{}");
        return "user/add";
    }


    /**
     * 打开编辑类别页面
     */
    @GetMapping("/category/update/html")
    public String categoryUpdateHtml() {
        log.info("HtmlJumpController.categoryUpdateHtml业务结束，结果:{}");
        return "category/edit";
    }

    @GetMapping("/category/save/html")
    public String categorySaveHtml() {
        log.info("HtmlJumpController.categorySaveHtml结束，结果:{}");
        return "category/add";
    }


    /**
     * 商品保存页面跳转
     *
     * @return
     */
    @GetMapping("/product/save/html")
    public String productSaveHtml(Model model) {
        log.info("HtmlJumpController.productSaveHtml业务结束，结果:{}");

        //查询类别列表,存入共享域
        R r = categoryClient.list();
        List<LinkedHashMap> data = (List<LinkedHashMap>) r.getData();

        List<Category> list = new ArrayList<>();
        for (LinkedHashMap map : data) {
            Category category = new Category();
            category.setCategoryId((Integer) map.get("category_id"));
            category.setCategoryName((String) map.get("category_name"));
            list.add(category);
        }

        model.addAttribute("clist", list);
        return "product/add";
    }

    /**
     * 商品保存页面跳转
     *
     * @return
     */
    @GetMapping("/product/update/html")
    public String productUpdateHtml(Model model) {
        log.info("HtmlJumpController.productUpdateHtml业务结束，结果:{}");

        //查询类别列表,存入共享域
        R r = categoryClient.list();
        List<LinkedHashMap> data = (List<LinkedHashMap>) r.getData();

        List<Category> list = new ArrayList<>();
        for (LinkedHashMap map : data) {
            Category category = new Category();
            category.setCategoryId((Integer) map.get("category_id"));
            category.setCategoryName((String) map.get("category_name"));
            list.add(category);
        }
        model.addAttribute("clist", list);
        return "product/edit";
    }
}
