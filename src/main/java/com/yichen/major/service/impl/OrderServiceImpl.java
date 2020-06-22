package com.yichen.major.service.impl;

import com.yichen.core.dto.order.OrderDTO;
import com.yichen.core.enums.OrderStatusEnum;
import com.yichen.core.param.order.OrderParam;
import com.yichen.exception.BusinessException;
import com.yichen.major.entity.FileMartial;
import com.yichen.major.entity.Order;
import com.yichen.major.manager.PhotoFixManager;
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
import org.springframework.util.CollectionUtils;

import java.nio.file.Path;
import java.nio.file.Paths;
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

    private final PhotoFixManager photoFixManager;

    public OrderServiceImpl(FileRepository fileRepository, OrderRepository orderRepository, RedisLockRegistry redisLockRegistry, PhotoFixManager photoFixManager) {
        this.fileRepository = fileRepository;
        this.orderRepository = orderRepository;
        this.redisLockRegistry = redisLockRegistry;
        this.photoFixManager = photoFixManager;
    }

    @Override
    public Page<OrderDTO> page(OrderParam param) {
        PageRequest request = PageRequest.of(param.getPageParam().getNum(), param.getPageParam().getSize());
        Page<Order> page = orderRepository.findByAccountId(param.getUserId(), request);
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
        param.getFileList().forEach(s -> fileRepository.findById(s).ifPresent(fileList::add));
        order.setFileList(fileList);
        if(!CollectionUtils.isEmpty(fileList)){
            orderRepository.save(order);
            changeStatus(order, OrderStatusEnum.DEAL);
            generatePhoto(fileList,order);
        }
        OrderDTO dto = new OrderDTO();
        BeanUtils.copyProperties(order, dto);
        return Optional.of(dto);
    }

    /**
     * 生成图片
     * @param files 原始文件列表
     * @param order 订单实体,生成图片完成完成该订单
     */
    private void generatePhoto(List<FileMartial> files,Order order) {
        files.forEach(fileMartial -> {
            String fileName = fileMartial.getId() + PhotoFixManager.getPhotoSuffix(fileMartial.getFileType());
            Path path = Paths.get(fileMartial.getFilePath(), fileName);
            try {
                photoFixManager.fixPhoto(path.toString());
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new BusinessException("文件转换出错!请联系管理员!");
            }
        });
        order.setCompleteTime(LocalDateTime.now());
        changeStatus(order, OrderStatusEnum.FINISH);
    }

    /**
     * 改变订单状态
     * @param order 订单实体
     * @param status 改变后的订单状态
     */
    private void changeStatus(Order order, OrderStatusEnum status){
        order = orderRepository.findById(order.getId()).orElseThrow(() -> new BusinessException("订单修改状态失败!"));
        order.setOrderStatus(status);
        orderRepository.save(order);

    }
}
