package com.yichen.major.service.impl;

import com.google.common.collect.Lists;
import com.yichen.config.properties.StorageProperties;
import com.yichen.config.exception.FileStorageException;
import com.yichen.config.exception.StorageFileNotFoundException;
import com.yichen.exception.BusinessException;
import com.yichen.major.entity.FileMartial;
import com.yichen.major.manager.PhotoFixManager;
import com.yichen.major.repo.FileRepository;
import com.yichen.major.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author dengbojing
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class StorageServiceImpl implements StorageService {

    private final Path fileStorageLocation;

    private final Path outputDir;

    private final FileRepository fileRepo;


    @Autowired
    public StorageServiceImpl(StorageProperties storageProperties, FileRepository fileRepo) throws IOException {
        this.fileStorageLocation = Paths.get(storageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        this.outputDir = Paths.get(storageProperties.getOutputDir())
                .toAbsolutePath().normalize();
        this.fileRepo = fileRepo;
        List<Path> list = Lists.newArrayList(fileStorageLocation,outputDir);
        for (Path path : list) {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    public String store(MultipartFile file,String userId) throws Exception {
        checkNotNull(file.getOriginalFilename());
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileMartial martial = new FileMartial();
        martial.setFileType(file.getContentType());
        martial.setSize(file.getSize());
        martial.setFileName(fileName);
        martial.setFilePath(fileStorageLocation.toString());
        martial.setCustomerId(userId);
        String invalidCharacters = "..";
        if(fileName.contains(invalidCharacters)) {
            throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
        }
        fileRepo.save(martial);
        String outputFileName = martial.getId() + PhotoFixManager.getPhotoSuffix(file.getContentType());
        Path targetLocation = this.fileStorageLocation.resolve(outputFileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        //photoFixManager.fixPhoto(targetLocation.toString(), tmpDir.toString(), outputDir.toString());
        return martial.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public List<String> store(MultipartFile[] files,String userId) {
        List<String> fileIds = Stream.of(files).map(file -> {
            try {
                return this.store(file,userId);
            } catch (Exception e) {
                throw new BusinessException("上传失败");
            }
        }).collect(Collectors.toList());
        return fileIds;
    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public Path load(String fileName) {
        FileMartial file = fileRepo.findById(fileName).orElseThrow(() ->  new StorageFileNotFoundException("FileMartial not found " + fileName));
        return this.outputDir.resolve(fileName+PhotoFixManager.getPhotoSuffix(file.getFileType())).normalize();
    }

    @Override
    public Resource loadAsResource(String fileName) {
        try {
            Path filePath = this.load(fileName);
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("FileMartial not found " + fileName);
            }
        } catch (Exception ex) {
            throw new StorageFileNotFoundException("FileMartial not found " + fileName, ex);
        }
    }

}
