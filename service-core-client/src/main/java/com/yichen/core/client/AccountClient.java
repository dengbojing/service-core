package com.yichen.core.client;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author dengbojing
 */
@FeignClient("service-core")
public interface AccountClient {


}
