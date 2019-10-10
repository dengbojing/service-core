package com.yichen.core.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author dengbojing
 */
@FeignClient("service-project")
public interface TestClient {
    @RequestMapping("/hello/{name}")
    String test(@PathVariable("name") String name);

    @RequestMapping("/hello/name/{name}")
    String test2(@PathVariable("name") String name);
}
