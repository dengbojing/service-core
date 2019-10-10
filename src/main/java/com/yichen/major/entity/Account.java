package com.yichen.major.entity;

import com.yichen.major.entity.common.AbstractEntity;
import com.yichen.core.enums.AccountStatusEnum;
import com.yichen.core.enums.ChargeTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * @author dengbojing
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_account")
public class Account extends AbstractEntity {

    /**
     * 登录名
     */
    @Column(name = "c_login_name", columnDefinition = "VARCHAR(255) NOT NULL COMMENT '登录名'")
    private String loginName;

    /**
     * 真实姓名
     */
    @Column(name = "c_name", columnDefinition = "VARCHAR(255)  COMMENT '真实姓名'")
    private String name;

    /**
     * 电话号码
     */
    @Column(name = "c_phone", columnDefinition = "VARCHAR(255) NOT NULL COMMENT '电话号码'")
    private String phone;

    /**
     * 邮箱
     */
    @Column(name = "c_email", columnDefinition = "VARCHAR(255) COMMENT '邮箱'")
    private String email;

    /**
     * 密码
     */
    @Column(name = "c_password", columnDefinition = "VARCHAR(255) NOT NULL COMMENT '密码'")
    private String password;

    /**
     * 计费开始日期
     */
    @Column(name = "c_start_date", columnDefinition = "DATE COMMENT '计费开始日期'")
    private LocalDate startDate;

    /**
     * 计费结束日期
     */
    @Column(name = "c_end_date", columnDefinition = "DATE COMMENT '计费结束日期'")
    private LocalDate endDate;

    /**
     * 计费次数
     */
    @Column(name = "c_count", columnDefinition = "BIGINT(20) COMMENT '计费次数'")
    private Long count;

    /**
     * 计费类型
     */
    @Column(name = "c_chargeType", columnDefinition = "VARCHAR(50)  COMMENT '计费类型'")
    @Enumerated(EnumType.STRING)
    private ChargeTypeEnum chargeType;


    /**
     * 状态
     */
    @Column(name = "c_status", columnDefinition = "INT(11) NOT NULL COMMENT '用户状态'")
    @Enumerated(EnumType.ORDINAL)
    private AccountStatusEnum status;

    /**
     * 组织id
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "c_organization_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), columnDefinition = "CHAR(36) COMMENT '组织ID'")
    private Organization organization;

    /**
     * 上传文件列表
     */
    @OneToMany(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "c_upload_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private List<FileMartial> file;
}
