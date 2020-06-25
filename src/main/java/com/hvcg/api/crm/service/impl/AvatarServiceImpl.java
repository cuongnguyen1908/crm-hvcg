package com.hvcg.api.crm.service.impl;

import com.hvcg.api.crm.constant.AppConstants;
import com.hvcg.api.crm.entity.Avatar;
import com.hvcg.api.crm.exception.NotFoundException;
import com.hvcg.api.crm.repository.AvatarRepository;
import com.hvcg.api.crm.service.AvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Service
public class AvatarServiceImpl implements AvatarService {

    @Autowired
    private AvatarRepository avatarRepository;


    @Override
    public void storeFile(MultipartFile file, Avatar avatar) {
        if (!(file.getOriginalFilename().endsWith(AppConstants.PNG_FILE_FORMAT) || file.getOriginalFilename().endsWith(AppConstants.JPEG_FILE_FORMAT) || file.getOriginalFilename().endsWith(AppConstants.JPG_FILE_FORMAT))) {
            throw new NotFoundException("Invalid file format");
        }
//        Avatar avatar = new Avatar();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                throw new NotFoundException("Filename contains invalid path");
            }

            //copy file to location
            Path path = Paths.get("uploads/");
            InputStream inputStream = file.getInputStream();
            Files.copy(inputStream, path.resolve(file.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);
            avatar.setName(fileName);
            String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/uploads/")
                    .path(avatar.getName())
                    .toUriString();
            avatar.setUrl(url);
            this.avatarRepository.save(avatar);
        } catch (IOException e) {
            throw new NotFoundException("Could not store file");

        }
    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
//            Path filePath = Paths.get("/Users/nguyen/Documents/hvcg/api.crm/uploads/").resolve(fileName).normalize();
            Path filePath = Paths.get("./uploads").toAbsolutePath().resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new NotFoundException("File not found " + fileName);

            }

        } catch (MalformedURLException e) {
            throw new NotFoundException("File not found " + fileName, e);
        }
    }
}
