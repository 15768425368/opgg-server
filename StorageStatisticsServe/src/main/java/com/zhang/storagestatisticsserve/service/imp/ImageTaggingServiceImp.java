package com.zhang.storagestatisticsserve.service.imp;

import com.baidu.aip.imageclassify.AipImageClassify;
import com.zhang.storagestatisticsserve.config.ApiResponse;
import com.zhang.storagestatisticsserve.entity.WxUser;
import com.zhang.storagestatisticsserve.mapper.WxUserMapper;
import com.zhang.storagestatisticsserve.service.ImageTaggingService;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ImageTaggingServiceImp implements ImageTaggingService {
    @Resource
    WxUserMapper wxUserMapper;

    @Override
    public ApiResponse identify(Map<String, Object> params, String openId) throws IOException {
        String imageUrl = (String) params.get("imageUrl");

        if(imageUrl == null) {
            return ApiResponse.error("缺少图片链接");
        }
        if(openId == null) {
            return ApiResponse.error("缺少用户openid");
        }

        WxUser wxUser = wxUserMapper.loadByOpenId(openId);
        if(wxUser.getState() != 0) {
            return ApiResponse.ok("该用户已被禁用!");
        }


        String APP_ID = "67733425";
        String API_KEY = "RgPwJRD8I5V2SVYCQZnofhZ7";
        String SECRET_KEY = "XbctFHrTXXCoEtxsPPyIhcoMDNySnif5";

        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("baike_num", "0");

        // 初始化一个AipImageClassify
        AipImageClassify client = new AipImageClassify(APP_ID, API_KEY, SECRET_KEY);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(20000);

        byte[] byteArray = downloadFile(imageUrl);
        JSONObject res = client.advancedGeneral(byteArray, options);

        JSONArray jsonArray = new JSONArray(res.get("result").toString());
        List<Map<String, Object>> list = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Map<String, Object> map = new HashMap<>();
            map.put("score", jsonObject.getDouble("score"));
            map.put("root", jsonObject.getString("root"));
            map.put("tag", jsonObject.getString("keyword"));
            list.add(map);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("tags", list);
        return ApiResponse.ok(result);
    }


    public static byte[] downloadFile(String fileUrl) {
        try {
            URL url = new URL(fileUrl);
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
