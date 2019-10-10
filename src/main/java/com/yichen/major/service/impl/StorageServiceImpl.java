package com.yichen.major.service.impl;

import com.yichen.config.properties.StorageProperties;
import com.yichen.config.exception.FileStorageException;
import com.yichen.config.exception.StorageFileNotFoundException;
import com.yichen.major.entity.FileMartial;
import com.yichen.major.repo.FileRepository;
import com.yichen.major.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

    private final String invalidCharacters = "..";

    private final FileRepository fileRepo;


    @Autowired
    public StorageServiceImpl(StorageProperties storageProperties, FileRepository fileRepo) throws IOException {
        this.fileStorageLocation = Paths.get(storageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        this.fileRepo = fileRepo;
        if (!Files.exists(fileStorageLocation)) {
            Files.createDirectories(fileStorageLocation);
        }
    }

    @Override
    public String store(MultipartFile file) throws IOException {
        checkNotNull(file.getOriginalFilename());
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileMartial martial = new FileMartial();
        martial.setFileType(file.getContentType());
        martial.setSize(file.getSize());
        martial.setFileName(fileName);
        martial.setFilePath(fileStorageLocation.toString());
        if(fileName.contains(invalidCharacters)) {
            throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
        }
        fileRepo.save(martial);
        Path targetLocation = this.fileStorageLocation.resolve(martial.getId());
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        return martial.getId();

    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public Path load(String fileName) {
        return null;
    }

    @Override
    public Resource loadAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("FileMartial not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new StorageFileNotFoundException("FileMartial not found " + fileName, ex);
        }
    }

}
