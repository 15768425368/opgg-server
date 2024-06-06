package com.zhang.storagestatisticsserve.entity;

import java.util.Date;

/**
 * @description 共享房间用户关联表
 * @author zhang
 * @date 2024-04-24
 */
public class WxRoomUser {
    private static final long serialVersionUID = 1L;

    /**
     * 房间内用户记录id
     */
    private Integer id;

    /**
     * 用户openid
     */
    private String userOpenId;

    /**
     * 房间id
     */
    private Integer roomId;

    /**
     * 是否可编辑
     */
    private Integer isEdit;

    /**
     * 是否可删除
     */
    private Integer isDelete;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


    public WxRoomUser() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserOpenId() {
        return userOpenId;
    }

    public void setUserOpenId(String userOpenId) {
        this.userOpenId = userOpenId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(Integer isEdit) {
        this.isEdit = isEdit;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
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
