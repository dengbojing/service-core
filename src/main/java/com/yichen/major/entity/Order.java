package com.yichen.major.entity;

import com.yichen.core.enums.OrderStatusEnum;
import com.yichen.major.entity.common.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author dengbojing
 */
@Entity
@Table(name = "t_order")
@Getter
@Setter
public class Order extends AbstractEntity {

    /**
     * 账号id
     */
    @Column(name = "c_account_id", columnDefinition = "varchar(36) NOT NULL COMMENT '关联账号id'")
    private String accountId;

    /**
     * 上传文件列表
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "c_order_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private List<FileMartial> fileList;

    /**
     * 完成时间
     */
    @Column(name = "c_complete_time", columnDefinition = "datetime COMMENT '完成时间'")
    private LocalDateTime completeTime;

    /**
     * 创建时间
     */
    @Column(name = "c_start_time", columnDefinition = "datetime NOT NULL COMMENT '创建时间'")
    private LocalDateTime startTime;

    /**
     * 状态
     */
    @Column(name = "c_status", columnDefinition = "INT(11) NOT NULL COMMENT '用户状态'")
    @Enumerated(EnumType.ORDINAL)
    private OrderStatusEnum orderStatus;

}
