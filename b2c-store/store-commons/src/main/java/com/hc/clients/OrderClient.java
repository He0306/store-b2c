package com.hc.clients;

import com.hc.param.PageParam;
import com.hc.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: 何超
 * @date: 2022/11/18
 */
@FeignClient(value = "order-service")
public interface OrderClient {

    @PostMapping("/order/remove/check")
    R removeCheck(@RequestBody Integer productId);

    @PostMapping("/order/admin/list")
    R adminList(@RequestBody PageParam pageParam);
}
