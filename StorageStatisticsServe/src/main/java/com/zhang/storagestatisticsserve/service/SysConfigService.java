package com.zhang.storagestatisticsserve.service;

import com.zhang.storagestatisticsserve.entity.SysConfig;

import java.util.Map;

public interface SysConfigService {
    /**
     * 新增
     */
    public Object insert(SysConfig sysConfig);

    /**
     * 删除
     */
    public Object delete(int id);

    /**
     * 更新
     */
    public Object update(SysConfig sysConfig);

    /**
     * 根据主键 id 查询
     */
    public SysConfig load(int id);

    /**
     * 分页查询
     */
    public Map<String,Object> pageList(int offset, int pagesize);

}
