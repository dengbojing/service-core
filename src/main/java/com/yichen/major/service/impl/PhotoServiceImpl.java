package com.yichen.major.service.impl;

import com.yichen.config.properties.StorageProperties;
import com.yichen.major.entity.FileMartial;
import com.yichen.major.entity.Photo;
import com.yichen.major.manager.PhotoFixManager;
import com.yichen.major.repo.FileRepository;
import com.yichen.major.repo.PhotoRepository;
import com.yichen.major.service.PhotoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author dengbojing
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepo;

    private final FileRepository fileRepo;

    private final Path downloadPath;

    private final PhotoFixManager photoFixManager;

    public PhotoServiceImpl(PhotoRepository photoRepo, FileRepository fileRepo, StorageProperties storageProperties, PhotoFixManager photoFixManager) {
        this.photoRepo = photoRepo;
        this.fileRepo = fileRepo;
        this.downloadPath = Paths.get(storageProperties.getOutputDir());
        this.photoFixManager = photoFixManager;
    }


    @Override
    public Integer save(List<String> files) {
        files.forEach(s -> {
            Photo photo = new Photo();
            FileMartial file = fileRepo.findById(s).orElseGet(FileMartial::new);
            photo.setPhotoMartial(file);
            Path path = Paths.get(downloadPath.toString(), file.getId() + photoFixManager.getPhotoSuffix(file.getFileType()));
            File fileTmp = new File(path.toUri());
            photo.setPhotoName(fileTmp.getName());
            photo.setPhotoPath(downloadPath.toString());
            photo.setSize(fileTmp.length());
            photo.setType(file.getFileType());
            photoRepo.save(photo);
        });
        return files.size();
    }
}
