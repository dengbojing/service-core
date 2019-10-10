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
    Page<Order> findByAccountId(String accountId, Pageable pageable);
}
