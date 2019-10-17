package com.yichen.major.service;

import java.util.List;

/**
 * @author dengbojing
 */
public interface PhotoService {
    /**
     * 保存生成照片
     * @param fileList 原始照片ID
     * @return 保存数量
     */
    Integer save(List<String> fileList);
}
