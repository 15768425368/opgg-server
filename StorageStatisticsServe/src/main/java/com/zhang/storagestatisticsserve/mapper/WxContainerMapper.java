package com.zhang.storagestatisticsserve.mapper;

import com.zhang.storagestatisticsserve.entity.WxContainer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description wx_container
 * @author zhang
 * @date 2024-04-09
 */
@Mapper
public interface WxContainerMapper {

    /**
     * 新增
     * @author zhang
     * @date 2024/04/09
     **/
    int insert(WxContainer wxContainer);

    /**
     * 刪除
     * @author zhang
     * @date 2024/04/09
     **/
    int delete(int id);

    /**
     * 更新
     * @author zhang
     * @date 2024/04/09
     **/
    int update(WxContainer wxContainer);

    /**
     * 查询 根据主键 id 查询
     * @author zhang
     * @date 2024/04/09
     **/
    WxContainer load(int id);

    /**
     * 查询 分页查询
     * @author zhang
     * @date 2024/04/09
     **/
    List<WxContainer> pageList(@Param("offset") int offset,@Param("pageSize") int pageSize, @Param("name") String name, @Param("openid") String openid, @Param("state") String state);

    /**
     * 查询 分页查询 count
     * @author zhang
     * @date 2024/04/09
     **/
    int pageListCount(@Param("offset") int offset,@Param("pageSize") int pageSize,@Param("name") String name, @Param("openid") String openid,  @Param("state") String state);

    /**
     * 根据id获取container列表
     * @param id
     * @return
     */
    List<WxContainer> getContainerById(String id);

    /**
     * 根据openid获取容器列表
     * @param openid
     * @return
     */
    List<WxContainer> getContainerByOpenId(String openid);
}