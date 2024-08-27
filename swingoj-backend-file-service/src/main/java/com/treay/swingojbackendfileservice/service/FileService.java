package com.treay.swingojbackendfileservice.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Treay
 */
public interface FileService {
    /**
     * 上传头像到OSS
     *
     * @param file
     * @return
     */
    String uploadFileAvatar(MultipartFile file);
}
