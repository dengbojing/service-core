package com.yichen.major.repo;

import com.yichen.major.entity.Billing;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author dengbojing
 */
public interface BillingRepository extends JpaRepository<Billing, String> {
}
