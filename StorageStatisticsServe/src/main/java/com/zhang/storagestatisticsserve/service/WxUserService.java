package com.zhang.storagestatisticsserve.service;

import com.zhang.storagestatisticsserve.config.ApiResponse;
import com.zhang.storagestatisticsserve.entity.WxUser;

import java.util.Map;

/**
 * @description wx_user
 * @author BEJSON
 * @date 2024-04-09
 */
public interface WxUserService {

    /**
     * 新增
     */
    public ApiResponse insert(WxUser wxUser);

    /**
     * 删除
     */
    public Object delete(int id);

    /**
     * 更新
     */
    public Object update(WxUser wxUser);

    /**
     * 根据主键 id 查询
     */
    public WxUser load(int id);

    /**
     * 根据openid获取用户信息
     * @param openid
     * @return
     */
    public WxUser loadByOpenId(String openid);

    /**
     * 分页查询
     */
    public Map<String,Object> pageList(int offset, int pagesize);

}