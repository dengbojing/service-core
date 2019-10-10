package com.yichen.major.service.impl;

import com.yichen.core.dto.order.OrderDTO;
import com.yichen.core.enums.OrderStatusEnum;
import com.yichen.core.param.order.OrderParam;
import com.yichen.major.entity.FileMartial;
import com.yichen.major.entity.Order;
import com.yichen.major.repo.FileRepository;
import com.yichen.major.repo.OrderRepository;
import com.yichen.major.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author dengbojing
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl implements OrderService {

    private final FileRepository fileRepository;

    private final OrderRepository orderRepository;

    private final RedisLockRegistry redisLockRegistry;

    public OrderServiceImpl(FileRepository fileRepository, OrderRepository orderRepository, RedisLockRegistry redisLockRegistry) {
        this.fileRepository = fileRepository;
        this.orderRepository = orderRepository;
        this.redisLockRegistry = redisLockRegistry;
    }

    @Override
    public Page<OrderDTO> page(OrderParam param) {
        PageRequest request = PageRequest.of(param.getPageParam().getNum(), param.getPageParam().getSize());
        Page<Order> page = orderRepository.findByAccountId(param.getAccountId(), request);
        return page.map(order -> {
            OrderDTO dto = new OrderDTO();
            BeanUtils.copyProperties(order,dto);
            return dto;
        });
    }

    @Override
    public Optional<OrderDTO> create(OrderParam param) {
        Order order = new Order();
        order.setAccountId(param.getUserId());
        order.setStartTime(LocalDateTime.now());
        order.setOrderStatus(OrderStatusEnum.INIT);
        List<FileMartial> fileList = new ArrayList<>();
        param.getFileList().forEach(s -> fileRepository.findById(s).ifPresent(fileMartial1 -> {
            fileMartial1.setOrderId(order.getId());
            fileList.add(fileMartial1);
        }));
        order.setFileList(fileList);
        orderRepository.save(order);
        OrderDTO dto = new OrderDTO();
        BeanUtils.copyProperties(order, dto);
        return Optional.of(dto);
    }
}
