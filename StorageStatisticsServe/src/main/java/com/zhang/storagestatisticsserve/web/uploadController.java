package com.zhang.storagestatisticsserve.web;

import com.zhang.storagestatisticsserve.config.ApiResponse;
import com.zhang.storagestatisticsserve.service.UploadService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/api/upload", produces = "application/json")
public class uploadController {
    @Resource
    private UploadService uploadService;


    /**
     * 上传图片至阿里云oss
     * @return
     */
    @PostMapping("/uploadImage")
    public ApiResponse uploadImage (@RequestParam("file") MultipartFile file, @RequestHeader("openid") String openid) {
        return uploadService.uploadImage(file, openid);
    }
}
