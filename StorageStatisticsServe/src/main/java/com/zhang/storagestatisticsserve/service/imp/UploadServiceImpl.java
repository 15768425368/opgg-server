package com.zhang.storagestatisticsserve.service.imp;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyuncs.exceptions.ClientException;
import com.zhang.storagestatisticsserve.config.ApiResponse;
import com.zhang.storagestatisticsserve.service.UploadService;
import com.zhang.storagestatisticsserve.utils.OssUpload;
import com.zhang.storagestatisticsserve.utils.ThrowError;
import com.zhang.storagestatisticsserve.utils.utils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Service
public class UploadServiceImpl implements UploadService {
    @Override
    public ApiResponse uploadImage(MultipartFile file, String openid) {
        try {
            if(file.isEmpty()) {
                return ApiResponse.error("缺少file文件");
            }

            if(!new utils().isImage(file)) {
                return ApiResponse.error("请上传图片类型文件");
            }

            // 获取文件名
            String originalFilename = file.getOriginalFilename();
            // 提取文件后缀名
            String fileExtension = "";
            if (originalFilename != null && !originalFilename.isEmpty()) {
                int dotIndex = originalFilename.lastIndexOf('.');
                if (dotIndex >= 0 && dotIndex < originalFilename.length() - 1) {
                    fileExtension = originalFilename.substring(dotIndex + 1);
                }
            }

            String newFileName = openid + new Date().getTime() + "." + fileExtension;
            OssUpload ossClient = new OssUpload(newFileName);
            Object result = ossClient.UploadImage(file);

            return ApiResponse.ok(result);
        } catch (ThrowError e) {
            return ApiResponse.error(e.getMessage());
        } catch (ClientException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteImage(String bucketName, String fileUrl) {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-beijing.aliyuncs.com";

        String accessKeyId = "LTAI5tGYZf6KnHypiiYXgwh4";
        String accessKeySecret = "3vMJKNJ0aELx58R1FamnQt3jvbWQA7";
        CredentialsProvider credentialsProvider = new DefaultCredentialProvider(accessKeyId, accessKeySecret);

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, credentialsProvider);
        try {
            // 删除文件或目录。如果要删除目录，目录必须为空。
            int lastSlashIndex = fileUrl.lastIndexOf('/');
            String objectName = "image/" + fileUrl.substring(lastSlashIndex + 1);
            ossClient.deleteObject(bucketName, objectName);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return 0;
    }
}
