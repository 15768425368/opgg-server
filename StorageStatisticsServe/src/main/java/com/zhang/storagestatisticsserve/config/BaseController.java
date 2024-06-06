package com.zhang.storagestatisticsserve.config;

import com.zhang.storagestatisticsserve.entity.WxUser;
import com.zhang.storagestatisticsserve.service.WxUserService;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.annotation.Resource;

/**
 * web层通用数据处理
 *
 * @author zhang
 */
public class BaseController {
    @Resource
    private WxUserService wxUserService;

    /**
     * 根据header openid参数获取用户信息
     * @param openId
     * @return
     */
    protected WxUser getWxUserInfoByHeaderOpenId (@RequestHeader("openid") String openId) {
        return  wxUserService.loadByOpenId(openId);
    }
}
