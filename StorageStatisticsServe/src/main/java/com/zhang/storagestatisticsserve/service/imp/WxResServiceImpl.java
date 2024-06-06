package com.zhang.storagestatisticsserve.service.imp;

import com.zhang.storagestatisticsserve.config.ApiResponse;
import com.zhang.storagestatisticsserve.entity.WxContainer;
import com.zhang.storagestatisticsserve.entity.WxRes;
import com.zhang.storagestatisticsserve.mapper.WxContainerMapper;
import com.zhang.storagestatisticsserve.mapper.WxResMapper;
import com.zhang.storagestatisticsserve.service.WxResService;
import com.zhang.storagestatisticsserve.utils.ThrowError;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @description wx_res
 * @author zhang
 * @date 2024-04-10
 */
@Service
public class WxResServiceImpl implements WxResService {

    @Resource
    private WxResMapper wxResMapper;

    @Resource
    private WxContainerMapper wxContainerMapper;


    @Override
    public ApiResponse insert(WxRes wxRes, String openid) {
        try {
            if (wxRes == null) {
                return ApiResponse.error("必要参数缺失");
            }

            if(openid.isEmpty() || openid == null) {
                return ApiResponse.error("缺少用户所属openid");
            }

            if(wxRes.getContainerId() == null) {
                return ApiResponse.error("缺少容器id");
            }

            if(wxRes.getName().isEmpty() || wxRes.getName() == null) {
                return ApiResponse.error("缺少物件名称");
            }

            wxRes.setCreateTime(new Date());
            wxRes.setUpdateTime(new Date());
            wxRes.setOpenid(openid);

            wxResMapper.insert(wxRes);
            return ApiResponse.ok();
        } catch (ThrowError e) {
            return ApiResponse.error(e.getMessage());
        }
    }


    @Override
    public Object delete(int id) {
        int ret = wxResMapper.delete(id);
        return ret>0?ApiResponse.ok():ApiResponse.error("系统错误!");
    }


    @Override
    public Object update(WxRes wxRes) {
        if(wxRes.getId() == null) {
            return ApiResponse.error("必要参数缺失!");
        }
        wxRes.setUpdateTime(new Date());
        int ret = wxResMapper.update(wxRes);
        return ret>0?ApiResponse.ok():ApiResponse.error("系统错误!");
    }


    @Override
    public WxRes load(int id) {
        return wxResMapper.load(id);
    }


    @Override
    public Map<String,Object> pageList(int offset, int pagesize) {

        List<WxRes> pageList = wxResMapper.pageList(offset, pagesize);
        int totalCount = wxResMapper.pageListCount(offset, pagesize);

        // result
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("pageList", pageList);
        result.put("totalCount", totalCount);

        return result;
    }

    @Override
    public ApiResponse getResByContainerId(Integer id) {
        if(id == null) {
            return ApiResponse.error("缺少容器id");
        }

        List<WxRes> pageList = wxResMapper.getResByContainerId(id);

        return ApiResponse.ok(pageList);
    }

    @Override
    public ApiResponse getResByNameTree(Map<String, Object> params, String openid) {

        String name = (String) params.get("name");
        if(name == null) {
            name = "";
        }

        if(openid.isEmpty() || openid == null) {
            return ApiResponse.error("缺少openid!");
        }

        // result
        Map<String, Object> result = new HashMap<String, Object>();

        // 获取物件列表
        List<WxRes> resListFromOpenid = wxResMapper.getResByOpenid(openid, name);
        if(resListFromOpenid.size() == 0) {
            result.put("pageList", resListFromOpenid);
            result.put("totalCount", 0);
            return ApiResponse.ok(result);
        }

        // 物件列表根据containerId 合并成新的树形结构list
        // 使用 Map 来分组对象
        Map<Integer, List<WxRes>> containerMap = new HashMap<>();
        for (WxRes res : resListFromOpenid) {
            int containerId = res.getContainerId();
            if (!containerMap.containsKey(containerId)) {
                containerMap.put(containerId, new ArrayList<>());
            }
            containerMap.get(containerId).add(res);
        }

        // 构建树形结构的数组
        List<Map<String, Object>> resTreeList = new ArrayList<>();
        for (Integer containerId : containerMap.keySet()) {
            WxContainer container = wxContainerMapper.load(containerId);
            if(Objects.equals(container.getState(), "0")) {
                Map<String, Object> containerNode = new HashMap<>();
                containerNode.put("containerId", containerId);
                containerNode.put("containerName", container.getName());
                containerNode.put("containerCover", container.getCover());
                containerNode.put("containerPosition", container.getPosition());
                containerNode.put("createTime", container.getCreateTime());
                containerNode.put("children", containerMap.get(containerId));
                resTreeList.add(containerNode);
            }
        }


        if(name == "") {
            List<WxContainer> wxContainers = wxContainerMapper.getContainerByOpenId(openid);

            for(WxContainer res : wxContainers) {
                int tag = 0;
                for(Map<String, Object> child : resTreeList) {
                    if(child.get("containerId") == res.getId()){
                        tag = 1;
                    }
                }

                if(tag == 0) {
                    Map<String, Object> containerNode = new HashMap<>();
                    containerNode.put("containerId", res.getId());
                    containerNode.put("containerName", res.getName());
                    containerNode.put("containerCover", res.getCover());
                    containerNode.put("containerPosition", res.getPosition());
                    containerNode.put("createTime", res.getCreateTime());
                    containerNode.put("children", new ArrayList<>());
                    resTreeList.add(containerNode);
                }
            }
        }


        result.put("pageList", resTreeList);
        result.put("totalCount", resTreeList.size());
//        System.out.println(resTreeList);

        return ApiResponse.ok(result);
    }
}