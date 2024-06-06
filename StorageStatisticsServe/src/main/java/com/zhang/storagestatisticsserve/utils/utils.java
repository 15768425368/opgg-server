package com.zhang.storagestatisticsserve.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class utils {
    /**
     * 判断文件类型是否为图片
     * @param file
     * @return
     */
    public boolean isImage(MultipartFile file) {
        try {
            // 获取文件的 MIME 类型
            String contentType = file.getContentType();

            // 判断文件类型
            if (contentType != null && !contentType.startsWith("image/")) {
                // 如果不是图片类型
                // 处理非图片类型文件上传的逻辑
                return false;
            } else {
                // 图片类型
                // 处理图片上传的逻辑
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
