package com.zhang.storagestatisticsserve.entity;

import java.util.Date;

public class WxRes {
    private static final long serialVersionUID = 1L;

    /**
     * 物件id
     */
    private Integer id;

    /**
     * 所属容器id
     */
    private Integer containerId;

    /**
     * 当物件没有所属容器时，自定义物件位置
     */
    private String resPosition;

    /**
     * 物件封面
     */
    private String cover;

    /**
     * 物件名称
     */
    private String name;

    /**
     * 用户所属openid
     */
    private String openid;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


    public WxRes() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getContainerId() {
        return containerId;
    }

    public void setContainerId(Integer containerId) {
        this.containerId = containerId;
    }

    public String getResPosition() {
        return resPosition;
    }

    public void setResPosition(String resPosition) {
        this.resPosition = resPosition;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
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
