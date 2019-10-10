package com.yichen.major.entity.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @author dengbojing
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(generator = "system-uuid", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID", length = 32, updatable = false, columnDefinition = "CHAR(36) NOT NULL COMMENT '主键'")
    private String id;

    @CreatedDate
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "CREATE_AT", updatable = false, columnDefinition = "DATETIME NOT NULL COMMENT '创建时间'")
    private Date createAt;

    @LastModifiedDate
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "UPDATE_AT", columnDefinition = "DATETIME NOT NULL COMMENT '更新时间'")
    private Date updateAt;

    @CreatedBy
    @Column(name = "CREATE_BY", updatable = false, columnDefinition = "CHAR(255) NOT NULL COMMENT '创建人'")
    private String createBy;

    @LastModifiedBy
    @Column(name = "UPDATE_BY", columnDefinition = "CHAR(255) NOT NULL COMMENT '更新人'")
    private String updateBy;


    public void copyFrom(AbstractEntity entity) {
        setCreateBy(entity.getCreateBy());
        setUpdateBy(entity.getUpdateBy());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractEntity)) {
            return false;
        }

        AbstractEntity that = (AbstractEntity) o;

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
