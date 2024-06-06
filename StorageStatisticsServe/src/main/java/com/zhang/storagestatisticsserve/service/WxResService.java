package com.zhang.storagestatisticsserve.service;

import com.zhang.storagestatisticsserve.config.ApiResponse;
import com.zhang.storagestatisticsserve.entity.WxRes;

import java.util.Map;

/**
 * @description wx_res
 * @author zhang
 * @date 2024-04-10
 */
public interface WxResService {

    /**
     * 新增
     */
    public ApiResponse insert(WxRes wxRes, String openid);

    /**
     * 删除
     */
    public Object delete(int id);

    /**
     * 更新
     */
    public Object update(WxRes wxRes);

    /**
     * 根据主键 id 查询
     */
    public WxRes load(int id);

    /**
     * 分页查询
     */
    public Map<String,Object> pageList(int offset, int pagesize);

    public ApiResponse getResByContainerId(Integer id);

    public ApiResponse getResByNameTree(Map<String, Object> params, String openid);
}