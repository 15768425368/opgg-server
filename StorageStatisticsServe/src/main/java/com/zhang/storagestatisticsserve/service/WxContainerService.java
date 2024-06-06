package com.zhang.storagestatisticsserve.service;

import com.zhang.storagestatisticsserve.config.ApiResponse;
import com.zhang.storagestatisticsserve.entity.WxContainer;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * @description wx_container
 * @author zhang
 * @date 2024-04-09
 */
public interface WxContainerService {

    /**
     * 新增
     */
    public Object insert(WxContainer wxContainer);

    /**
     * 删除
     */
    public Object delete(int id);

    /**
     * 更新
     */
    public Object update(WxContainer wxContainer);

    /**
     * 根据主键 id 查询
     */
    public WxContainer load(int id);

    /**
     * 分页查询
     */
    public ApiResponse pageList(@RequestBody Map<String, Object> params, String openid);

    /**
     * 根据openid获取容器列表
     * @param openid
     * @return
     */
    public List<WxContainer> getContainerByOpenId(String openid);
}