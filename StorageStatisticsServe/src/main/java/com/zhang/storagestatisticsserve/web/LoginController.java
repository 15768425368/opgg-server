package com.zhang.storagestatisticsserve.web;

import com.zhang.storagestatisticsserve.config.ApiResponse;
import com.zhang.storagestatisticsserve.config.wxServe;
import com.zhang.storagestatisticsserve.entity.WxUser;
import com.zhang.storagestatisticsserve.service.WxUserService;
import com.zhang.storagestatisticsserve.utils.ThrowError;
import com.zhang.storagestatisticsserve.utils.WxHttp;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class LoginController {
    @Resource
    private WxUserService wxUserService;

    @PostMapping("/login")
    public Object login (@RequestBody WxUser wxUser) {
        try {
            WxUser userInfo = wxUserService.loadByOpenId(wxUser.getOpenid());

            if(userInfo == null){
                // 新用户
                WxUser insertUser = new WxUser();
                insertUser.setNickName("收纳官" + new Random().nextInt(900) + 100);
                insertUser.setAvatar("https://zhangdaxingtest.oss-cn-beijing.aliyuncs.com/systemImage/default-head-img.png");
                insertUser.setOpenid(wxUser.getOpenid());
                insertUser.setGender(0);
                insertUser.setState(0);
               return wxUserService.insert(insertUser);
            }else {
                // 老用户
                wxUserService.update(userInfo);
                return ApiResponse.ok(userInfo);
            }
        } catch (ThrowError e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    @GetMapping("/getOpenId")
    public Object getOpenId (@RequestParam String code) {
        try {
            wxServe wx = new wxServe();
            Map<String, Object> wxParams = new HashMap<>();
            wxParams.put("js_code", code);
            wxParams.put("appid", wx.getAppid());
            wxParams.put("secret", wx.getSecret());
            wxParams.put("grant_type", "authorization_code");
            return new WxHttp().getOpenId(wxParams);
        } catch (ThrowError e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    @GetMapping("/userInfo")
    public ApiResponse getUserInfo(@RequestHeader("openid") String openid){
        try {
            WxUser user = wxUserService.loadByOpenId(openid);
            wxUserService.update(user);
            if(user != null) {
                return ApiResponse.ok(user);
            }else {
                return ApiResponse.error("系统错误!");
            }

        } catch (ThrowError e) {
            return ApiResponse.error(e.getMessage());
        }
    }
}
