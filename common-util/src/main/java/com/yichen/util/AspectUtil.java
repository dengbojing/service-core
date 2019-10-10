package com.yichen.util;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author dengbojing
 */
public class AspectUtil {
    /**
     * 是否含有文件上传参数
     *
     * @param args 参数数组
     * @return true含有
     */
    public static boolean hasFileArg(Object[] args) {
        for (Object arg : args) {
            if (arg instanceof MultipartFile) {
                return true;
            }
        }
        return false;
    }
}
