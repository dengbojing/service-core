package com.yichen.api;

import com.yichen.major.service.StorageService;
import com.yichen.core.dto.file.FileDTO;
import com.yichen.response.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/uploadMultipleFiles/{userId}")
    public List<CommonResponse<FileDTO>> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files,@PathVariable("userId") String userId)  {
        return Arrays.stream(files)
                .map(file -> this.uploadFile(file,userId))
                .collect(Collectors.toList());
    }

    @PostMapping("/upload/{userId}")
    public CommonResponse<FileDTO> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("userId") String userId) {
        String fileName = null;
        log.info(userId);
        try {
            fileName = storageService.store(file);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return CommonResponse.success(FileDTO.builder().fileName(fileName).build());
    }





}
