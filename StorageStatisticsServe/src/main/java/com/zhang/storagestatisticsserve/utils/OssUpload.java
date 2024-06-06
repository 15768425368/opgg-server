package com.zhang.storagestatisticsserve.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class OssUpload {
    // Endpoint
    private String endpoint = "https://oss-cn-beijing.aliyuncs.com";

    // Bucket名称
    private String bucketName = "zhangdaxingtest";

    // Object完整路径
    private String objectName;

    // 指定本地路径
    private String filePath;

    public OssUpload (String fileName) throws ClientException {
        objectName = "image/" + fileName;
    }

    public Object UploadImage(MultipartFile file) throws ClientException {
        // 从环境变量中获取RAM用户的访问密钥（AccessKey ID和AccessKey Secret）。
//        String accessKeyId = System.getenv("OSS_ACCESS_KEY_ID");
//        String accessKeySecret = System.getenv("OSS_ACCESS_KEY_SECRET");
        String accessKeyId = "*************";
        String accessKeySecret = "*****************";
        // 从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
        CredentialsProvider credentialsProvider = new DefaultCredentialProvider(accessKeyId, accessKeySecret);

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, credentialsProvider);

        try {
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, file.getInputStream());
            // 上传文件。
            PutObjectResult result = ossClient.putObject(putObjectRequest);

            String fileUrl = "https://" + bucketName + ".oss-cn-beijing.aliyuncs.com/" + objectName;
            return fileUrl;
        } catch (OSSException oe) {
            return oe;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}
