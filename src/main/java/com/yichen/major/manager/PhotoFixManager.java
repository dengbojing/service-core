package com.yichen.major.manager;

import com.google.common.collect.Lists;
import com.yichen.config.properties.StorageProperties;
import com.yichen.core.param.file.FileParam;
import com.yichen.core.param.order.OrderParam;
import com.yichen.exception.BusinessException;
import com.yichen.major.service.AccountService;
import com.yichen.major.service.OrderService;
import com.yichen.major.service.PhotoService;
import com.yichen.major.service.StorageService;
import com.yichen.util.SimpleMultipartFile;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import sScardPhotoFixer.ApiKey;
import sScardPhotoFixer.SScardPhotoFixer;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

/**
 * @author dengbojing
 */
@Component
public class PhotoFixManager {

    private static final String PNG = "image/png";

    private static final String X_PNG = "image/x-png";

    private static final String P_JPEG = "image/pjpeg";

    private static final String JPEG = "image/jpeg";

    private static final String BMP = "image/bmp";

    private final Path tmpDir;

    private final Path targetDir;

    private final AccountService accountService;

    private final StorageService storageService;

    @Resource
    private OrderService orderService;

    private final PhotoService photoService;


    public PhotoFixManager(StorageProperties storageProperties, AccountService accountService,StorageService storageService,   PhotoService photoService) throws IOException {
        this.accountService = accountService;
        this.photoService = photoService;
        this.storageService = storageService;
        ApiKey.setKey(storageProperties.getApiKey());
        ApiKey.setSecretKey(storageProperties.getSecretKey());
        this.tmpDir = Paths.get(storageProperties.getTmpDir())
                .toAbsolutePath().normalize();
        this.targetDir = Paths.get(storageProperties.getOutputDir())
                .toAbsolutePath().normalize();
        List<Path> list = Lists.newArrayList(tmpDir,targetDir);
        for (Path path : list) {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        }
    }


    public void fixPhoto(String sourceFileName) throws Exception {
        SScardPhotoFixer.fix1PhotoNoLog(sourceFileName,tmpDir.toString(),targetDir.toString());
    }

    public byte[] fixPhoto(FileParam param,String userId) throws Exception {
        SimpleMultipartFile file = new SimpleMultipartFile(param.getFileName(), Base64.getDecoder().decode(param.getFileContent()));
        if(file.getSize() < 14 * 1024 || file.getSize() > 40 * 1024){
            throw new BusinessException("文件大小必须在14-40k之间");
        }
        if(file.getWidth() != 358 && file.getHeight() != 411){
            throw new BusinessException("文件尺寸不符合要求，要求尺寸为：宽：358，高：411。");
        }

        String fileId = storageService.store(file,userId);
        OrderParam orderParam = new OrderParam();
        orderParam.setUserId(userId);
        orderParam.setFileList(Lists.newArrayList(fileId));
        orderService.create(orderParam)
                .filter(param1-> StringUtils.isNotEmpty(param1.getId()))
                .orElseThrow(() -> new BusinessException("转换出错!"));
        photoService.save(orderParam.getFileList());
        accountService.decrease(orderParam.getUserId(), 1);
        return Files.readAllBytes(storageService.load(fileId));
    }

    public static String getPhotoSuffix(@NotNull String contentType){
        String suffix = "";
        if(X_PNG.equalsIgnoreCase(contentType) || PNG.equalsIgnoreCase(contentType)){
            suffix = SuffixHolder.PNG;
        }
        if(P_JPEG.equalsIgnoreCase(contentType) || JPEG.equalsIgnoreCase(contentType)){
            suffix = SuffixHolder.JPG;
        }
        if(BMP.equalsIgnoreCase(contentType)){
            suffix = SuffixHolder.BMP;
        }
        return suffix;
    }

    public static class SuffixHolder{
        public static final String JPG = ".jpg";
        public static final String PNG = ".png";
        public static final String BMP = ".bmp";
    }
}
