package com.yichen.major.service.impl;

import com.yichen.core.dto.org.OrganizationDTO;
import com.yichen.core.param.org.OrganizationParam;
import com.yichen.major.entity.Organization;
import com.yichen.major.repo.OrganizationRepository;
import com.yichen.major.service.OrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author dengbojing
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public Optional<Organization> addOrganization(OrganizationParam organizationParam) {
        Organization org = new Organization();
        BeanUtils.copyProperties(organizationParam, org);
        return Optional.of(organizationRepository.save(org));
    }

    @Override
    public Integer deleteById(String id) {
        organizationRepository.deleteById(id);
        return 1;
    }

    @Override
    public Integer updateOrganization(OrganizationParam organizationParam) {
        Organization org = new Organization();
        BeanUtils.copyProperties(organizationParam, org);
        return 1;
    }

    @Override
    public Page<OrganizationDTO> getByOrgName(OrganizationParam organizationParam) {
        PageRequest pageRequest = PageRequest.of(organizationParam.getPageParam().getNum(), organizationParam.getPageParam().getSize());
        return organizationRepository.findByName(organizationParam.getName(), pageRequest).map(org -> {
            OrganizationDTO dto = new OrganizationDTO();
            BeanUtils.copyProperties(org, dto);
            return dto;
        });
    }
}
