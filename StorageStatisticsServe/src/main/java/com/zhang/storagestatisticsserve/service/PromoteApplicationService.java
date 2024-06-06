package com.zhang.storagestatisticsserve.service;

import com.zhang.storagestatisticsserve.entity.PromoteApplication;

import java.util.Map;

public interface PromoteApplicationService {
    /**
     * 新增
     */
    public Object insert(PromoteApplication promoteApplication);

    /**
     * 删除
     */
    public Object delete(int id);

    /**
     * 更新
     */
    public Object update(PromoteApplication promoteApplication);

    /**
     * 根据主键 id 查询
     */
    public PromoteApplication load(int id);

    /**
     * 分页查询
     */
    public Map<String,Object> pageList(Map<String, Object> params);
}
