package com.yichen.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author dengbojing
 */
@Getter
@Setter
@ConfigurationProperties(ApiProperties.PREFIX)
public class ApiProperties {

    private Boolean enabled = true;
    public static final String PREFIX = "com.yichen.api";

    private Integer step = 20;

    private Integer ms = 400;

    private Boolean log = true;
}

