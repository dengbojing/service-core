package com.yichen.core.dto.file;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dengbojing
 */
@Getter
@Setter
@Builder
public class FileDTO {

    private String id;
    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件存储路径
     */
    private String filePath;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 文件上传人id
     */
    private String customerId;

    /**
     * 订单id
     */
    private String orderId;
}
