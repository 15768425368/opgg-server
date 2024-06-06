package com.zhang.storagestatisticsserve.service;

import com.zhang.storagestatisticsserve.config.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    /**
     * 上传图片
     * @param file
     * @return
     */
    public ApiResponse uploadImage(MultipartFile file, String openid);

    /**
     * 根据文件名删除oss文件
     * @param bucketName Bucket名称
     * @param objectName 文件完整路径中不能包含Bucket名称
     * @return
     */
    public int deleteImage(String bucketName, String objectName);
}
