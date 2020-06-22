package com.yichen.util;

import com.yichen.exception.BusinessException;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dengbojing
 */
public class SimpleMultipartFile implements MultipartFile {

    private String name;
    private byte[] content;
    private boolean empty = true;
    private String contentType;
    public SimpleMultipartFile(String name,byte[] content){
        this.name = name;
        if(content !=null && content.length > 0){
            this.content = content;
            empty = false;
            contentType = FileInfoParser.getFileType(content);
            if(StringUtils.isEmpty(contentType)){
                throw new BusinessException("不能识别的文件类型");
            }
        }else{
            throw new BusinessException("文件内容不能为空");
        }
    }


    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getOriginalFilename() {
        return this.name;
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public boolean isEmpty() {
        return this.empty;
    }

    @Override
    public long getSize() {
        return content.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return content;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(content);
    }

    @Override
    public void transferTo(File destination) throws IOException, IllegalStateException {
        Files.write(destination.toPath(), Base64.getDecoder().decode(content), StandardOpenOption.CREATE);
    }
}
