package com.yichen.major.entity;


import com.yichen.major.entity.common.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * 登录日志
 * @author dengbojing
 */
@Getter
@Setter
@Entity
@Table(name = "t_login_history")
@EntityListeners(AuditingEntityListener.class)
public class LoginHistory extends AbstractEntity {

    @Column(name = "c_host", columnDefinition = "VARCHAR ( 255 ) COMMENT '主机'")
    private String host;

    @Column(name = "c_customer_id", columnDefinition = "CHAR ( 36 ) COMMENT '用户'")
    private String customerId;

    @Column(name = "c_login_name", columnDefinition = "VARCHAR ( 255 )  COMMENT '登录邮箱'")
    private String loginName;

    @Column(name = "c_organization_id", columnDefinition = "CHAR ( 36 ) COMMENT '组织'")
    private String organizationId;

    @Column(name = "c_status", columnDefinition = "VARCHAR ( 255 ) COMMENT '登录状态'")
    private String status;

}
