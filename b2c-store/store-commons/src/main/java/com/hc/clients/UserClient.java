package com.hc.clients;

import com.hc.param.CartListParam;
import com.hc.param.PageParam;
import com.hc.pojo.User;
import com.hc.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: 何超
 * @date: 2022/11/17
 */
@FeignClient(value = "user-service")
public interface UserClient {

    @PostMapping("/user/admin/list")
    R listPage(@RequestBody PageParam pageParam);

    @PostMapping("/user/admin/remove")
    R remove(@RequestBody CartListParam cartListParam);

    @PostMapping("/user/admin/update")
    R update(@RequestBody User user);

    @PostMapping("/user/admin/save")
    R save(@RequestBody User user);
}
