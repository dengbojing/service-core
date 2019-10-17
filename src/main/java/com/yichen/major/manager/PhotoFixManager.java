package com.yichen.major.manager;

import com.google.common.collect.Lists;
import com.yichen.config.properties.StorageProperties;
import org.springframework.stereotype.Component;
import sScardPhotoFixer.ApiKey;
import sScardPhotoFixer.SScardPhotoFixer;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public PhotoFixManager(StorageProperties storageProperties) throws IOException {
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

    public String getPhotoSuffix(@NotNull String contentType){
        String suffix = "";
        if(X_PNG.equalsIgnoreCase(contentType) || PNG.equalsIgnoreCase(contentType)){
            suffix = ".png";
        }
        if(P_JPEG.equalsIgnoreCase(contentType) || JPEG.equalsIgnoreCase(contentType)){
            suffix = ".jpg";
        }
        if(BMP.equalsIgnoreCase(contentType)){
            suffix = ".bmp";
        }
        return suffix;
    }
}
