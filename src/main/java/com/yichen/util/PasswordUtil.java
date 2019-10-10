package com.yichen.util;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

/**
 * @author dengbojing
 */
@Component
public class PasswordUtil {
    private static final String key = "yichen";

    /**
     * 获取密码
     *
     * @param src 原始值
     * @return 加密值
     */
    public String encode(String src) {
        return DigestUtils.md5DigestAsHex((src.toLowerCase() + key).getBytes());
    }
}
