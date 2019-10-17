package com.yichen.major.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author dengbojing
 */
public interface StorageService {


    /**
     * 文件上传
     * @param file 文件
     * @return 文件名称
     * @throws IOException {@link IOException}
     */
    String store(MultipartFile file,String userId) throws Exception;

    /**
     * 多文件上传
     * @param files 文件数组
     * @return 文件名称
     */
    List<String> store(MultipartFile[] files,String userId);

    /**
     * 获取所有文件
     * @return 文件溜
     */
    Stream<Path> loadAll();

    /**
     * 获取单个文件
     * @param filename 文件名称
     * @return 文件路径
     */
    Path load(String filename);

    /**
     * 获取文件资源
     * @param fileName 文件名称
     * @return 文件资源
     */
    Resource loadAsResource(String fileName);

}
