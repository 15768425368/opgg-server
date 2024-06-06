package com.zhang.storagestatisticsserve.service;

import com.zhang.storagestatisticsserve.config.ApiResponse;

import java.io.IOException;
import java.util.Map;

public interface ImageTaggingService {
    /**
     * 图像标签识别服务
     * @param params
     * @param openId
     * @return
     */
    public ApiResponse identify(Map<String, Object> params, String openId) throws IOException;
}
