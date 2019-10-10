package com.yichen.major.entity;

import com.yichen.major.entity.common.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

/**
 * @author dengbojing
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_photo")
public class Photo extends AbstractEntity {


    @Column(name = "c_photo_name", columnDefinition = "CHAR(255) COMMENT '生成证件照名称'")
    private String photoName;

    @Column(name = "c_photo_path", columnDefinition = "CHAR(255) COMMENT '生成证件照存储路径'")
    private String photoPath;

    @Column(name = "c_photo_size", columnDefinition = "BIGINT COMMENT '生成文件大小'")
    private Long size;

    @Column(name = "c_photo_type", columnDefinition = "CHAR(255) COMMENT '生成文件类型'")
    private String type;


    @OneToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "c_photo_martial_id", referencedColumnName = "id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), columnDefinition = "CHAR(36) COMMENT '原始文件id'")
    private FileMartial photoMartial;

}
