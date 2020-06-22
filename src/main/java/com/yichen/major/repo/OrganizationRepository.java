package com.yichen.major.repo;

import com.yichen.major.entity.Organization;
import com.yichen.util.JsonUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author dengbojing
 */
public interface OrganizationRepository extends JpaRepository<Organization, String> {

    /**
     * 根据名称分页查询组织机构
     * @param name 组织机构名称
     * @param pageable 分页参数
     * @return 分页结果
     */
    Page<Organization> findByNameLike(String name, Pageable pageable);
}
