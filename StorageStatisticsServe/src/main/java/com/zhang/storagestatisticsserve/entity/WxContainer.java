package com.zhang.storagestatisticsserve.entity;

import java.util.Date;

/**
 * @description wx_container
 * @author zhang
 * @date 2024-04-09
 */
public class WxContainer {

    private static final long serialVersionUID = 1L;

    /**
     * 容器id
     */
    private Integer id;

    /**
     * 容器封面
     */
    private String cover;

    /**
     * 容器名称
     */
    private String name;

    /**
     * 容器位置
     */
    private String position;

    /**
     * 容器状态：0正常、1软删除
     */
    private String state;

    /**
     * 容器所属用户openid
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


    public WxContainer() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getOpenid() {
        return openid;
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