package com.zhang.storagestatisticsserve.entity;

import java.util.Date;

/**
 * @description 共享房间表
 * @author zhang
 * @date 2024-04-24
 */
public class WxJointRoom {
    private static final long serialVersionUID = 1L;

    /**
     * 协同房间id
     */
    private Integer id;

    /**
     * 房间名称
     */
    private String roomName;

    /**
     * 房主openId
     */
    private String hostUserOpenId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


    public WxJointRoom() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getHostUserOpenId() {
        return hostUserOpenId;
    }

    public void setHostUserOpenId(String hostUserOpenId) {
        this.hostUserOpenId = hostUserOpenId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
