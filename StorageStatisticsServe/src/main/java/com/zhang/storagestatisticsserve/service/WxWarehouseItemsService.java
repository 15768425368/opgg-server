package com.zhang.storagestatisticsserve.service;

import com.zhang.storagestatisticsserve.config.ApiResponse;
import com.zhang.storagestatisticsserve.entity.WxWarehouseItems;

import java.util.Map;

public interface WxWarehouseItemsService {

    /**
     * 新增
     */
    public ApiResponse insert(WxWarehouseItems wxWarehouseItems, String openId);

    /**
     * 删除
     */
    public ApiResponse delete(int id, String openid);

    /**
     * 更新
     */
    public Object update(WxWarehouseItems wxWarehouseItems);

    /**
     * 根据主键 id 查询
     */
    public WxWarehouseItems load(int id, String openid);

    /**
     * 分页查询
     */
    public Map<String,Object> pageList(int offset, int pagesize);

    /**
     * 软删除物品
     * @param id
     * @return
     */
    public ApiResponse deleteItem(Integer id, String openId);

    /**
     * 根据oepnid获取已经被用户软删除的物品列表
     * @param openid
     * @return
     */
    public ApiResponse getRecycleBinByOpenId(String openid);

    /**
     * 根据id恢复物品状态
     * @param id
     * @return
     */
    public ApiResponse recoverRecycleBinById(Integer id, String openid);

    /**
     * 兼容共享空间，分页查询物品列表
     * @return
     */
    public ApiResponse getListAndRoom(Map<String, Object> params, String openid);
}
