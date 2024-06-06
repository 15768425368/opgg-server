package com.zhang.storagestatisticsserve.web;

import com.zhang.storagestatisticsserve.config.ApiResponse;
import com.zhang.storagestatisticsserve.service.ImageTaggingService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/imageTagging", produces = "application/json")
public class ImageTaggingController {
    @Resource
    ImageTaggingService imageTaggingService;

    /**
     * 图像标签识别
     * @param params
     * @param openId
     * @return
     */
    @PostMapping("/identify")
    public ApiResponse identify(@RequestBody Map<String, Object> params, @RequestHeader("openid") String openId) throws IOException {
        return imageTaggingService.identify(params, openId);
    }
}
