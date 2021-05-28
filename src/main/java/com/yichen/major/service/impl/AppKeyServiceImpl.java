package com.yichen.major.service.impl;

import com.alibaba.fastjson.JSON;
import com.yichen.major.entity.AppKey;
import com.yichen.major.repo.AppKeyRepository;
import com.yichen.major.service.AppKeyService;
import com.yichen.util.OpenApiStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

/**
 * @author dengbojing
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class AppKeyServiceImpl implements AppKeyService {

    private final AppKeyRepository appKeyRepo;

    public AppKeyServiceImpl(AppKeyRepository appKeyRepo) {
        this.appKeyRepo = appKeyRepo;
    }

    @Override
    public OpenApiStatus check(String key,String token,Long timeSpan) {
        log.info("key is {}, token is {}",key ,token);
        OpenApiStatus status = OpenApiStatus.TIME_EXPIRED;
        if(StringUtils.isEmpty(token) || StringUtils.isEmpty(key) || timeSpan == null){
            status = OpenApiStatus.EMPTY_HEADER;
        }
        if(System.currentTimeMillis() / 1000 - timeSpan < 10*60){
            StringBuilder secretKey = new StringBuilder();
            AppKey appKey = appKeyRepo.findByAppKey(key).orElseGet(AppKey::new);
            if(StringUtils.isEmpty(appKey.getId()) || appKey.getAccount() == null){
                status = OpenApiStatus.APP_KEY_EXPIRED;
            }else{
                secretKey.append(appKey.getSecretKey());
                String validateToken = DigestUtils.md5DigestAsHex(key.concat(timeSpan+"").concat(secretKey.toString()).getBytes());
                log.info("find appKey Object {} , secretKey is {}", JSON.toJSONString(appKey.getAccount().getName()), secretKey);
                if( token.equalsIgnoreCase(validateToken)){
                    status = OpenApiStatus.OK;
                }else{
                    status = OpenApiStatus.VALIDATE_FAILURE;
                }
            }
        }
        return status;
    }

    @Override
    public String getAccountId(String key) {
        AppKey appKey = appKeyRepo.findByAppKey(key).orElseGet(AppKey::new);
        return appKey.getAccount().getId();
    }
}
