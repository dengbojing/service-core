package com.yichen.major.service;

import com.yichen.core.dto.org.OrganizationDTO;
import com.yichen.core.param.org.OrganizationParam;
import com.yichen.major.entity.Organization;
import org.springframework.data.domain.Page;

import java.util.Optional;

/**
 * @author dengbojing
 */
public interface OrganizationService {

    /**
     * 添加组织机构
     * @param organizationParam 参数
     * @return 受影响行数
     */
    Optional<Organization> addOrganization(OrganizationParam organizationParam);

    /**
     * 删除组织机构
     * @param id 参数
     * @return 受影响行数
     */
    Integer deleteById(String id);

    /**
     * 更新组织机构
     * @param organizationParam 参数
     * @return 受影响行数
     */
    Integer updateOrganization(OrganizationParam organizationParam);

    /**
     * 分页获取组织机构
     * @param organizationParam 组织机构参数
     * @return 分页结果
     */
    Page<OrganizationDTO> getByOrgName(OrganizationParam organizationParam);

    /**
     * 添加或者更新组织机构
     * @param param 组织机构参数
     */
    void saveOrUpdate(OrganizationParam param);
}
