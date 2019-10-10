package com.yichen.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
/*
*
 * @author dengbojing
 */

/**
 * @author dengbojing
 */
@Getter
@Setter
@ConfigurationProperties(ExceptionProperties.PREFIX)
public class ExceptionProperties {

    private Boolean enabled = true;

    public static final String PREFIX = "com.yichen.global.exception";
}

