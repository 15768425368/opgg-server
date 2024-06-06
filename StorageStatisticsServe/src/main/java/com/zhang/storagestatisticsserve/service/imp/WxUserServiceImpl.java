package com.zhang.storagestatisticsserve.service.imp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhang.storagestatisticsserve.config.ApiResponse;
import com.zhang.storagestatisticsserve.entity.WxUser;
import com.zhang.storagestatisticsserve.entity.WxWarehouseItems;
import com.zhang.storagestatisticsserve.mapper.WxUserMapper;
import com.zhang.storagestatisticsserve.mapper.WxWarehouseItemsMapper;
import com.zhang.storagestatisticsserve.service.WxUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @description wx_user
 * @author BEJSON
 * @date 2024-04-09
 */
@Service
public class WxUserServiceImpl implements WxUserService {

    @Resource
    private WxUserMapper wxUserMapper;

    @Resource
    private WxWarehouseItemsMapper wxWarehouseItemsMapper;


    @Override
    public ApiResponse insert(WxUser wxUser) {

        // valid
        if (wxUser == null) {
            return ApiResponse.error("必要参数缺失");
        }

        wxUser.setCreateTime(new Date());
        wxUser.setUpdateTime(new Date());

        wxUserMapper.insert(wxUser);

        // 初始化收纳数据
        try {
            formatNewUser(wxUser.getOpenid());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return ApiResponse.ok(wxUser);
    }


    @Override
    public Object delete(int id) {
        int ret = wxUserMapper.delete(id);
        return ret>0?ApiResponse.ok():ApiResponse.error("系统错误!");
    }


    @Override
    public Object update(WxUser wxUser) {

        wxUser.setUpdateTime(new Date());
        int ret = wxUserMapper.update(wxUser);
        return ret>0?ApiResponse.ok():ApiResponse.error("系统错误!");
    }


    @Override
    public WxUser load(int id) {
        return wxUserMapper.load(id);
    }

    @Override
    public WxUser loadByOpenId(String openid) { return  wxUserMapper.loadByOpenId(openid); };


    @Override
    public Map<String, Object> pageList(int offset, int pagesize) {

        List<WxUser> pageList = wxUserMapper.pageList(offset, pagesize);
        int totalCount = wxUserMapper.pageListCount(offset, pagesize);

        // result
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("pageList", pageList);
        result.put("totalCount", totalCount);

        return result;
    }


    public void formatNewUser(String openId) throws JsonProcessingException {
        List<WxWarehouseItems> list = new ArrayList<>();

        String json = "[{\"id\":\"\",\"pid\":null,\"openId\":\"\",\"state\":\"0\",\"name\":\"珍珠项链\",\"cover\":\"https://zhangdaxingtest.oss-cn-beijing.aliyuncs.com/systemImage/opPGH5JiL1PZWX5J4u5fctnRJBhQ1713862950927.jpg\",\"place\":\"\",\"isShared\":\"0\",\"createTime\":\"\",\"updateTime\":\"\"},{\"id\":\"\",\"pid\":null,\"openId\":\"\",\"state\":\"0\",\"name\":\"耳钉套装\",\"cover\":\"https://zhangdaxingtest.oss-cn-beijing.aliyuncs.com/systemImage/opPGH5JiL1PZWX5J4u5fctnRJBhQ1713862984628.jpg\",\"place\":\"\",\"isShared\":\"0\",\"createTime\":\"\",\"updateTime\":\"\"},{\"id\":\"\",\"pid\":null,\"openId\":\"\",\"state\":\"0\",\"name\":\"戒指\",\"cover\":\"https://zhangdaxingtest.oss-cn-beijing.aliyuncs.com/systemImage/opPGH5JiL1PZWX5J4u5fctnRJBhQ1713863033411.jpg\",\"place\":\"\",\"isShared\":\"0\",\"createTime\":\"\",\"updateTime\":\"\"},{\"id\":\"\",\"pid\":null,\"openId\":\"\",\"state\":\"0\",\"name\":\"白色卫衣\",\"cover\":\"https://zhangdaxingtest.oss-cn-beijing.aliyuncs.com/systemImage/opPGH5JiL1PZWX5J4u5fctnRJBhQ1713863092694.jpeg\",\"place\":\"\",\"isShared\":\"0\",\"createTime\":\"\",\"updateTime\":\"\"},{\"id\":\"\",\"pid\":null,\"openId\":\"\",\"state\":\"0\",\"name\":\"体重秤\",\"cover\":\"https://zhangdaxingtest.oss-cn-beijing.aliyuncs.com/systemImage/opPGH5JiL1PZWX5J4u5fctnRJBhQ1713863137831.jpg\",\"place\":\"\",\"isShared\":\"0\",\"createTime\":\"\",\"updateTime\":\"\"},{\"id\":\"\",\"pid\":null,\"openId\":\"\",\"state\":\"0\",\"name\":\"白鞋子\",\"cover\":\"https://zhangdaxingtest.oss-cn-beijing.aliyuncs.com/systemImage/opPGH5JiL1PZWX5J4u5fctnRJBhQ1713863200608.jpeg\",\"place\":\"\",\"isShared\":\"0\",\"createTime\":\"\",\"updateTime\":\"\"}]";
        ObjectMapper mapper = new ObjectMapper();
        List<WxWarehouseItems> personList = mapper.readValue(json, new TypeReference<List<WxWarehouseItems>>() {});

        // 新建第一条
        WxWarehouseItems wxWarehouseItems = new WxWarehouseItems();
        wxWarehouseItems.setOpenId(openId);
        wxWarehouseItems.setState("0");
        wxWarehouseItems.setName("示例：木纹箱子");
        wxWarehouseItems.setCover("https://zhangdaxingtest.oss-cn-beijing.aliyuncs.com/systemImage/opPGH5JiL1PZWX5J4u5fctnRJBhQ1713862840477.jpg");
        wxWarehouseItems.setPlace("主卧室床底");
        wxWarehouseItems.setIsShared("0");
        wxWarehouseItems.setCreateTime(new Date());
        wxWarehouseItems.setUpdateTime(new Date());
        wxWarehouseItems.setCreateUser(openId);

        wxWarehouseItemsMapper.insert(wxWarehouseItems);

        List<WxWarehouseItems> userList = wxWarehouseItemsMapper.getListByOpenId(wxWarehouseItems);
        if (userList.size() > 0) {
            WxWarehouseItems items = userList.get(0);

            for (WxWarehouseItems child : personList) {
                child.setPid(items.getId());
                child.setOpenId(openId);
                Date timer = new Date();
                child.setCreateTime(timer);
                child.setUpdateTime(timer);
                child.setCreateUser(openId);
                wxWarehouseItemsMapper.insert(child);
            }
        }
    }
}
