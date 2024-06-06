package com.zhang.storagestatisticsserve.mapper;

import com.zhang.storagestatisticsserve.entity.WxJointRoom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description 共享房间表
 * @author zhang
 * @date 2024-04-24
 */
@Mapper
public interface WxJointRoomMapper {
    /**
     * 新增
     * @author zhang
     * @date 2024/04/24
     **/
    int insert(WxJointRoom wxJointRoom);

    /**
     * 刪除
     * @author zhang
     * @date 2024/04/24
     **/
    int delete(int id);

    /**
     * 更新
     * @author zhang
     * @date 2024/04/24
     **/
    int update(WxJointRoom wxJointRoom);

    /**
     * 查询 根据主键 id 查询
     * @author zhang
     * @date 2024/04/24
     **/
    WxJointRoom load(int id);

    /**
     * 查询 分页查询
     * @author zhang
     * @date 2024/04/24
     **/
    List<WxJointRoom> pageList(int offset, int pagesize);

    /**
     * 查询 分页查询 count
     * @author zhang
     * @date 2024/04/24
     **/
    int pageListCount(int offset,int pagesize);

    /**
     * 根据openid获取房间列表
     * @param openid
     * @return
     */
    List<WxJointRoom> getRoomListByOpenId(@Param("openid") String openid);
}
