package com.yichen.major.repo;

import com.yichen.major.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author dengbojing
 */
public interface OrderRepository extends JpaRepository<Order, String> {
    /**
     * 根据账户id分页查询订单
     * @param accountId 账户id
     * @param pageable 分页参数
     * @return 分页集合
     */
    Page<Order> findByAccountId(String accountId, Pageable pageable);
}
