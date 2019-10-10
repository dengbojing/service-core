package com.yichen.major.entity;

import com.yichen.major.entity.common.AbstractEntity;
import lombok.*;

import javax.persistence.*;

/**
 * @author dengbojing
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_photo_martial")
@Builder
public class FileMartial extends AbstractEntity {

    /**
     * 文件名称
     */
    @Column(name = "c_file_name", columnDefinition = "VARCHAR(255) NOT NULL COMMENT '文件名称'")
    private String fileName;

    /**
     * 文件存储路径
     */
    @Column(name = "c_file_path", columnDefinition = "VARCHAR(255) COMMENT '文件存储路径'")
    private String filePath;

    /**
     * 文件类型
     */
    @Column(name = "c_file_type",  columnDefinition = "VARCHAR(255) COMMENT '文件类型'")
    private String fileType;

    /**
     * 文件大小
     */
    @Column(name = "c_file_size", columnDefinition = "BIGINT COMMENT '文件存储大小'")
    private Long size;

    /**
     * 文件上传人id
     */
    @Column(name = "c_upload_id", columnDefinition = "CHAR(36) COMMENT '文件上传人id'")
    private String customerId;

    /**
     * 订单id
     */
    @Column(name = "c_order_id", columnDefinition = "CHAR(36) COMMENT '订单id'")
    private String orderId;


}
