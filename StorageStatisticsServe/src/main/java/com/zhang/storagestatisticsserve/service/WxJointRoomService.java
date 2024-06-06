package com.zhang.storagestatisticsserve.service;

import com.zhang.storagestatisticsserve.config.ApiResponse;
import com.zhang.storagestatisticsserve.entity.WxJointRoom;
import com.zhang.storagestatisticsserve.entity.WxRoomUser;

import java.util.Map;

/**
 * @description 共享房间表
 * @author zhang
 * @date 2024-04-24
 */
public interface WxJointRoomService {
    /**
     * 新增
     */
    public Object insert(WxJointRoom wxJointRoom);

    /**
     * 删除
     */
    public Object delete(int id);

    /**
     * 更新
     */
    public ApiResponse update(WxJointRoom wxJointRoom, String openId);

    /**
     * 根据主键 id 查询
     */
    public WxJointRoom load(int id);

    /**
     * 分页查询
     */
    public Map<String,Object> pageList(int offset, int pagesize);

    /**
     * 创建共享房间
     * @param wxJointRoom
     * @return
     */
    public ApiResponse createRoom(WxJointRoom wxJointRoom);

    /**
     * 加入共享空间
     * @param params
     * @param openId
     * @return
     */
    public ApiResponse joinRoom(Map<String, Object> params, String openId);

    /**
     * 解散共享空间
     * @param roomId
     * @param openId
     * @return
     */
    public ApiResponse disbandRoom(Integer roomId, String openId);

    /**
     * 退出共享空间
     * @param roomId
     * @param openId
     * @return
     */
    public ApiResponse quitRoom(Integer roomId, String openId);

    /**
     * 查询是否有共享空间
     * @param openid
     * @return
     */
    public ApiResponse isRoom(String openid);

    /**
     * 获取共享空间的用户列表
     * @param openid
     * @return
     */
    public ApiResponse getRoomUser(String openid);

    /**
     * 将成员移除共享空间
     * @param int
     * @return
     */
    public ApiResponse removeUserRoom(int id);

    /**
     * 编辑成员权限
     * @param wxRoomUser
     * @return
     */
    public ApiResponse editMemberPer(WxRoomUser wxRoomUser);
}
