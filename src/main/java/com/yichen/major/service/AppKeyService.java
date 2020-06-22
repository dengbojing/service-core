package com.yichen.major.service;

import com.yichen.util.OpenApiStatus;

/**
 * @author dengbojing
 */
public interface AppKeyService {

    /**
     * 校验请求签名是否正确
     * @param key 访问密钥
     * @param token 访问签名
     * @param timeSpan 访问时间
     */
    OpenApiStatus check(String key, String token, Long timeSpan);


    /**
     * 根据appkey获取账户id
     * @param appKey appkey
     * @return 账户id
     */
    String getAccountId(String appKey);
}
