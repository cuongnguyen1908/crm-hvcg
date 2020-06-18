package com.hvcg.api.crm.service;

import com.hvcg.api.crm.entity.Avatar;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface AvatarService {
    void storeFile(MultipartFile file, Avatar avatar);

    Resource loadFileAsResource(String fileName);
}
