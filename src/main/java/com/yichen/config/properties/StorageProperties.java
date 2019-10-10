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

    private String uploadDir = "/f/data/temp";

}
