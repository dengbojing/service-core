package com.yichen.api;

import com.google.common.collect.Lists;
import com.yichen.exception.BusinessException;
import com.yichen.major.service.OrderService;
import com.yichen.major.service.StorageService;
import com.yichen.core.dto.file.FileDTO;
import com.yichen.request.RequestHolder;
import com.yichen.response.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dengbojing
 */
@RestController
@RequestMapping("file")
@Slf4j
public class FileApi {

    private final StorageService storageService;


    @Autowired
    public FileApi(StorageService storageService) {
        this.storageService = storageService;
    }

    /**
     * 获取文件
     * @param filename 文件名称
     * @return {@link ResponseEntity<Resource>}
     */
    @GetMapping("/view/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    /**
     * 多文件上传
     * @param files 文件
     * @return {@link CommonResponse}
     */
    @PostMapping("/uploadMultipleFiles")
    public CommonResponse<List<String>> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files)  {
        String userId = RequestHolder.info().getUser().getUserId();
        List<String> fileIds = storageService.store(files,userId);
        return CommonResponse.success(fileIds);
    }

    /**
     * 文件上传
     * @param file 文件
     * @return 文件信息
     */
    @PostMapping("/upload")
    public CommonResponse<FileDTO> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        String userId = RequestHolder.info().getUser().getUserId();
        String fileId = storageService.store(file,userId);
        return CommonResponse.success(FileDTO.builder().fileName(fileId).build());
    }





}
