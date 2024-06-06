package com.zhang.storagestatisticsserve.service;

import com.zhang.storagestatisticsserve.entity.WxFeedback;

import java.util.Map;

public interface WxFeedbackService {
    /**
     * 新增
     */
    public Object insert(WxFeedback wxFeedback, String openId);

    /**
     * 删除
     */
    public Object delete(int id);

    /**
     * 更新
     */
    public Object update(WxFeedback wxFeedback);

    /**
     * 根据主键 id 查询
     */
    public WxFeedback load(int id);

    /**
     * 分页查询
     */
    public Map<String,Object> pageList(int offset, int pagesize);
}
