package com.zhang.storagestatisticsserve.utils;

import com.zhang.storagestatisticsserve.config.ApiResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class WxHttp {
    private String url = "https://api.weixin.qq.com";
    public ApiResponse getOpenId(Map wxParams) {
        try {
            // 创建 URL 对象
            URL url = new URL(this.url + "/sns/jscode2session?appid=" + wxParams.get("appid") + "&secret=" + wxParams.get("secret") + "&js_code=" + wxParams.get("js_code") + "&grant_type=authorization_code");

            // 创建 HttpURLConnection 对象
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            // 设置请求方法为 GET
            con.setRequestMethod("GET");

            // 发送请求
            int responseCode = con.getResponseCode();

            // 读取响应内容
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;

            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return ApiResponse.ok(response.toString());
        }catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage());
        }
    }
}
