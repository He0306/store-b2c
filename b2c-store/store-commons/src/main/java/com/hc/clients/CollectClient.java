package com.hc.clients;

import com.hc.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: 何超
 * @date: 2022/11/18
 */
@FeignClient(value = "collect-service")
public interface CollectClient {

    @PostMapping("/collect/remove/check")
    R removeCheck(@RequestBody Integer productId);
}
