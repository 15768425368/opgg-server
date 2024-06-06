package com.zhang.storagestatisticsserve.mapper;

import com.zhang.storagestatisticsserve.entity.WxRoomUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description 共享房间用户关联表
 * @author zhang
 * @date 2024-04-24
 */
@Mapper
public interface WxRoomUserMapper {
    /**
     * 新增
     * @author zhang
     * @date 2024/04/24
     **/
    int insert(WxRoomUser wxRoomUser);

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
    int update(WxRoomUser wxRoomUser);

    /**
     * 查询 根据主键 id 查询
     * @author zhang
     * @date 2024/04/24
     **/
    WxRoomUser load(int id);

    /**
     * 查询 分页查询
     * @author zhang
     * @date 2024/04/24
     **/
    List<WxRoomUser> pageList(int offset, int pagesize);

    /**
     * 查询 分页查询 count
     * @author zhang
     * @date 2024/04/24
     **/
    int pageListCount(int offset,int pagesize);

    /**
     * 根据openid 查询共享空间用户关联列表
     * @param openid
     * @return
     */
    List<WxRoomUser> getListByOpenId(@Param("openid") String openid);

    /**
     * 根据openid查询数据
     * @param openid
     * @return
     */
    WxRoomUser loadByOpenId(@Param("openid") String openid);

    /**
     * 根据openid与共享空间id查询数据
     * @param openid
     * @param id
     * @return
     */
    WxRoomUser loadByOpenIdAndId(@Param("openid") String openid, @Param("roomId") int roomId);

    /**
     * 根据roomid查询列表
     * @param roomId
     * @return
     */
    List<WxRoomUser> getListByRoomId(@Param("roomId") Integer roomId);

    /**
     * 多选删除
     * @param list
     */
    void deleteItems(List<Integer> list);
}
