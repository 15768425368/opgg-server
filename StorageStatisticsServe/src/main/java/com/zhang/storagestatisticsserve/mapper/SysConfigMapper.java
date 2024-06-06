package com.zhang.storagestatisticsserve.mapper;

import com.zhang.storagestatisticsserve.entity.SysConfig;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysConfigMapper {
    /**
     * 新增
     * @author zhang
     * @date 2024/04/18
     **/
    int insert(SysConfig sysConfig);

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
    int update(SysConfig sysConfig);

    /**
     * 查询 根据主键 id 查询
     * @author zhang
     * @date 2024/04/18
     **/
    SysConfig load(int id);

    /**
     * 查询 分页查询
     * @author zhang
     * @date 2024/04/18
     **/
    List<SysConfig> pageList(int offset, int pagesize);

    /**
     * 查询 分页查询 count
     * @author zhang
     * @date 2024/04/18
     **/
    int pageListCount(int offset,int pagesize);

}
