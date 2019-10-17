package com.yichen.major.service;

import com.yichen.core.dto.order.OrderDTO;
import com.yichen.core.param.order.OrderParam;
import com.yichen.major.entity.Order;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * @author dengbojing
 */
public interface OrderService {

    /**
     * 分页查询订单星系
     * @param param 查询参数
     * @return {@link Page}
     */
    Page<OrderDTO> page(OrderParam param);

    /**
     * 创建订单
     * @param param 订单参数
     * @return 订单详情
     */
    Optional<OrderDTO> create(OrderParam param);
}
