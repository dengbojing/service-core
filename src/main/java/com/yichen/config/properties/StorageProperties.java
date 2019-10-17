package com.yichen.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author dengbojing
 */
@Getter
@Setter
@ConfigurationProperties(StorageProperties.PREFIX)
public class StorageProperties {

    static final String PREFIX = "com.yichen.file";

    /**
     * 图片默认上传路径
     */
    private String uploadDir = "/f/data/upload";

    /**
     * 图片默认临时存储路径
     */
    private String tmpDir = "/f/data/temp";

    /**
     * 转换完成图片默认输出路径
     */
    private String outputDir = "/f/data/output";

    /**
     * 图片转换默认key
     */
    private String apiKey = "TIzalHKGKoSLCs23tCxJoFAt_bvNI7nc";

    /**
     * 图片转换默认密钥
     */
    private String secretKey = "nCk5XlcfthMIrnb00AEW_IfuRQcjG5AQ";

}
