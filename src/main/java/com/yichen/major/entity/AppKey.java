package com.yichen.major.entity;

import com.yichen.major.entity.common.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author dengbojing
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_app_key")
public class AppKey extends AbstractEntity {

    @OneToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "c_account_id",referencedColumnName = "id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), columnDefinition = "CHAR(36) COMMENT '账户id'")
    private Account account;

    @Column(name = "c_app_key",columnDefinition = "varchar(36) comment '访问密钥'")
    private String appKey;

    @Column(name = "c_secret_key", columnDefinition = "varchar(36) comment '加密密钥'")
    private String secretKey;

    @Column(name = "c_control_type", columnDefinition = "varchar(36) comment '访问类型, 预留'")
    private String controlType;

}
