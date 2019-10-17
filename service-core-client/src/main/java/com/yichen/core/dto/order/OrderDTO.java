package com.yichen.core.dto.order;

import com.yichen.core.dto.file.FileDTO;
import com.yichen.core.enums.OrderStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author dengbojing
 */
@Getter
@Setter
public class OrderDTO {

    private String id;
    /**
     * 账号id
     */
    private String accountId;

    /**
     * 上传文件列表
     */
    private List<FileDTO> fileList;

    /**
     * 完成时间
     */
    private LocalDateTime completeTime;

    /**
     * 创建时间
     */
    private LocalDateTime startTime;

    /**
     * 状态
     */
    private OrderStatusEnum orderStatus;

}
