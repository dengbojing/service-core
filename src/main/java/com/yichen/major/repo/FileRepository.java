package com.yichen.major.repo;

import com.yichen.major.entity.FileMartial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * @author dengbojing
 */
public interface FileRepository extends JpaRepository<FileMartial, String>, QuerydslPredicateExecutor<FileMartial>, JpaSpecificationExecutor<FileMartial> {

}
