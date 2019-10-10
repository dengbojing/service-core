package com.yichen.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yichen.properties.JwtProperties;
import com.yichen.properties.RedisProperties;
import com.yichen.util.JsonUtil;
import com.yichen.util.JwtUtil;
import com.yichen.util.RedisUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

/**
 * @author dengbojing
 */
@Configuration
public class CommonConfiguration {

    @Resource
    private ObjectMapper objectMapper;


    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = JwtProperties.PREFIX, name = "enabled", havingValue = "true")
    public JwtUtil jwtBean(){
        return new JwtUtil(objectMapper);
    }


    @Bean
    @ConditionalOnMissingBean
    public JsonUtil jsonBean(){
        return new JsonUtil(objectMapper);
    }

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = RedisProperties.PREFIX, name = "enabled", havingValue = "true")
    public RedisUtil redisBean(){
        return new RedisUtil(stringRedisTemplate,redisTemplate);
    }

}
