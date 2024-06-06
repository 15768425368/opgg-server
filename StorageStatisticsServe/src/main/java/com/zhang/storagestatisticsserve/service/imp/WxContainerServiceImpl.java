package com.zhang.storagestatisticsserve.service.imp;

import com.zhang.storagestatisticsserve.config.ApiResponse;
import com.zhang.storagestatisticsserve.entity.WxContainer;
import com.zhang.storagestatisticsserve.mapper.WxContainerMapper;
import com.zhang.storagestatisticsserve.service.WxContainerService;
import com.zhang.storagestatisticsserve.utils.ThrowError;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description wx_container
 * @author zhang
 * @date 2024-04-09
 */
@Service
public class WxContainerServiceImpl implements WxContainerService {

    @Resource
    private WxContainerMapper wxContainerMapper;


    @Override
    public Object insert(WxContainer wxContainer) {

        // valid
        if (wxContainer == null || wxContainer.getName() == null || wxContainer.getName() == "" || wxContainer.getOpenid() == null || wxContainer.getOpenid() == "") {
            return ApiResponse.error("必要参数缺失");
        }

        wxContainer.setCreateTime(new Date());
        wxContainer.setUpdateTime(new Date());
        wxContainer.setState("0");

        wxContainerMapper.insert(wxContainer);
        return ApiResponse.ok();
    }


    @Override
    public Object delete(int id) {
        // 软删除
//        int ret = wxContainerMapper.delete(id);
        WxContainer container = new WxContainer();
        container.setId(id);
        container.setState("1");
        int ret = wxContainerMapper.update(container);
        return ret>0?ApiResponse.ok():ApiResponse.error("系统错误!");
    }


    @Override
    public Object update(WxContainer wxContainer) {
        if (wxContainer.getId() == null ||  wxContainer == null || wxContainer.getName() == null || wxContainer.getName() == "" || wxContainer.getOpenid() == null || wxContainer.getOpenid() == "") {
            return ApiResponse.error("必要参数缺失");
        }
        wxContainer.setUpdateTime(new Date());
        int ret = wxContainerMapper.update(wxContainer);
        return ret>0?ApiResponse.ok():ApiResponse.error("系统错误!");
    }


    @Override
    public WxContainer load(int id) {
        return wxContainerMapper.load(id);
    }


    @Override
    public ApiResponse pageList(@RequestBody Map<String, Object> params, String openid) {
        try {
            if(openid == null || openid.isEmpty()){
                return ApiResponse.error("缺少openid参数!");
            }


            int offset = (int) params.get("offset");
            offset = offset -1;
            int pagesize = (int) params.get("pagesize");
            String keyWord = (String) params.get("name");
            String state = (String) params.get("state");

            List<WxContainer> pageList = wxContainerMapper.pageList(offset, pagesize, keyWord, openid, state);
            int totalCount = wxContainerMapper.pageListCount(offset, pagesize, keyWord, openid, state);

            // result
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("pageList", pageList);
            result.put("totalCount", totalCount);

            return ApiResponse.ok(result);
        } catch (ThrowError e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    @Override
    public List<WxContainer> getContainerByOpenId(String openid) {
        return wxContainerMapper.getContainerByOpenId(openid);
    }
}