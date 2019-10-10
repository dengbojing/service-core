package com.yichen.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author dengbojing
 */
@Getter
@Setter
@ConfigurationProperties(JwtProperties.PREFIX)
public class JwtProperties {
    public static final String PREFIX = "com.yichen.jwt";

    private Boolean enabled;
}
