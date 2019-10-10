package com.yichen.api;

import com.yichen.core.dto.order.OrderDTO;
import com.yichen.core.param.order.OrderParam;
import com.yichen.exception.BusinessException;
import com.yichen.major.service.OrderService;
import com.yichen.response.CommonResponse;
import org.springframework.data.domain.Page;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author dengbojing
 */
@RestController
@RequestMapping("/order")
public class OrderApi {

    private final OrderService orderService;

    public OrderApi(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/list")
    public CommonResponse<Page<OrderDTO>> list(@RequestBody OrderParam param){
        return CommonResponse.success(orderService.page(param));
    }

    @RequestMapping("/add")
    public CommonResponse create(@RequestBody OrderParam param){
        if(CollectionUtils.isEmpty(param.getFileList() )){
            throw new BusinessException("请先上传文件!");
        }
        orderService.create(param);
        return CommonResponse.success();
    }
}
