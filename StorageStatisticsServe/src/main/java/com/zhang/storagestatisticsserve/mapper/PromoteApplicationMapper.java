package com.zhang.storagestatisticsserve.mapper;

import com.zhang.storagestatisticsserve.entity.PromoteApplication;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PromoteApplicationMapper {
    /**
     * 新增
     * @author zhang
     * @date 2024/04/18
     **/
    int insert(PromoteApplication promoteApplication);

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
    int update(PromoteApplication promoteApplication);

    /**
     * 查询 根据主键 id 查询
     * @author zhang
     * @date 2024/04/18
     **/
    PromoteApplication load(int id);

    /**
     * 查询 分页查询
     * @author zhang
     * @date 2024/04/18
     **/
    List<PromoteApplication> pageList(@Param("offset") int offset,@Param("pagesize") int pagesize, @Param("state") int state);

    /**
     * 查询 分页查询 count
     * @author zhang
     * @date 2024/04/18
     **/
    int pageListCount(@Param("offset") int offset,@Param("pagesize") int pagesize, @Param("state") int state);
}
