package com.yichen.major.entity;

import com.yichen.major.entity.common.AbstractEntity;
import com.yichen.core.enums.OrganizationTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author dengbojing
 */
@Setter
@Getter
@Entity
@Table(name = "t_organization")
public class Organization extends AbstractEntity {

    @Column(name = "c_name", columnDefinition = "VARCHAR(255) NOT NULL COMMENT '名称'")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "c_type", columnDefinition = "VARCHAR(128) NOT NULL COMMENT '组织类型：PERSONAL，COMPANY'")
    private OrganizationTypeEnum type;
}
