package com.yichen.api;

import com.yichen.core.dto.order.OrderDTO;
import com.yichen.core.param.order.OrderParam;
import com.yichen.exception.BusinessException;
import com.yichen.major.service.AccountService;
import com.yichen.major.service.OrderService;
import com.yichen.major.service.PhotoService;
import com.yichen.response.CommonResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author dengbojing
 */
@RestController
@RequestMapping("/order")
public class OrderApi {

    private final OrderService orderService;

    private final AccountService accountService;

    private final PhotoService photoService;

    public OrderApi(OrderService orderService, AccountService accountService, PhotoService photoService) {
        this.orderService = orderService;
        this.accountService = accountService;
        this.photoService = photoService;
    }

    /**
     * 订单查询
     * @param param 插叙参数
     * @return 订单列表
     */
    @PostMapping("/page")
    public CommonResponse<Page<OrderDTO>> list(@RequestBody OrderParam param){
        return CommonResponse.success(orderService.page(param));
    }

    /**
     * 创建订单
     * @param param 订单参数
     * @return 是否成功
     */
    @RequestMapping("/add")
    public CommonResponse<OrderDTO> create(@RequestBody OrderParam param){
        //FIXME 考虑更好的设计----拆分功能, 使其符合单一职责
        accountService.check(param.getUserId());
        if(CollectionUtils.isEmpty(param.getFileList() ) ){
            throw new BusinessException("请先上传文件!");
        }
        OrderDTO dto = orderService.create(param)
                .filter(param1->StringUtils.isNotEmpty(param1.getId()))
                .orElseThrow(() -> new BusinessException("转换出错!"));
        photoService.save(param.getFileList());
        accountService.decrease(param.getUserId(),dto.getFileList().size());
        return CommonResponse.success(dto);
    }
}
