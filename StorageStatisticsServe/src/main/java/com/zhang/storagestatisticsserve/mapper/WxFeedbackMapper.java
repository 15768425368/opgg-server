package com.zhang.storagestatisticsserve.mapper;

import com.zhang.storagestatisticsserve.entity.WxFeedback;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WxFeedbackMapper {
    /**
     * 新增
     * @author zhang
     * @date 2024/04/18
     **/
    int insert(WxFeedback wxFeedback);

    /**
     * 刪除
     * @author zhang
     * @date 2024/04/18
     **/
    int delete(int id);

    /**
     * 更新
     * @author zhang
     * @date 2024/04/18
     **/
    int update(WxFeedback wxFeedback);

    /**
     * 查询 根据主键 id 查询
     * @author zhang
     * @date 2024/04/18
     **/
    WxFeedback load(int id);

    /**
     * 查询 分页查询
     * @author zhang
     * @date 2024/04/18
     **/
    List<WxFeedback> pageList(int offset, int pagesize);

    /**
     * 查询 分页查询 count
     * @author zhang
     * @date 2024/04/18
     **/
    int pageListCount(int offset,int pagesize);
}
