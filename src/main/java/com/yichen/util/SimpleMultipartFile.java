package com.yichen.util;

import com.yichen.exception.BusinessException;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Base64;

/**
 * @author dengbojing
 */
public class SimpleMultipartFile implements MultipartFile {

    private final String name;
    private final byte[] content;
    private boolean empty = true;
    private final String contentType;
    public SimpleMultipartFile(String name,byte[] content){
        this.name = name;
        if(content !=null && content.length > 0){
            this.content = content;
            empty = false;
            contentType = ImageInfoParser.getFileType(content);
            if(StringUtils.isEmpty(contentType)){
                throw new BusinessException("不能识别的文件类型");
            }
        }else{
            throw new BusinessException("文件内容不能为空");
        }
    }


    @NotNull
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

    /**
     * 文件实际大小按照base64编码规范获取，数组大小为文件占用空间
     * @return 文件实际大小
     */
    @Override
    public long getSize() {
        return content.length * 3L /4 ;
    }

    @NotNull
    @Override
    public byte[] getBytes() throws IOException {
        return content;
    }

    @NotNull
    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(content);
    }

    @Override
    public void transferTo(File destination) throws IOException, IllegalStateException {
        Files.write(destination.toPath(), Base64.getDecoder().decode(content), StandardOpenOption.CREATE);
    }

    public int getHeight() throws IOException {
        return ImageIO.read(getInputStream()).getHeight();
    }

    public int getWidth() throws IOException{
        return ImageIO.read(getInputStream()).getWidth();
    }
}
