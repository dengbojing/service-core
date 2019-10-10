package com.yichen.major.repo;

import com.yichen.major.entity.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author dengbojing
 */
public interface LoginHistoryRepository extends JpaRepository<LoginHistory, String> {
}
