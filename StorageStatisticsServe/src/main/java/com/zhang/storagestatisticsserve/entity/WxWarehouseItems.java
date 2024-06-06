package com.zhang.storagestatisticsserve.entity;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class WxWarehouseItems {
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    private Integer id;

    /**
     * 父级id
     */
    private Integer pid;

    /**
     * 父级名称
     */
    private String pidName;

    /**
     * 所属用户openid
     */
    private String openId;

    /**
     * 物品状态：0启用、1停用（软删除）
     */
    private String state;

    /**
     * 物品名称
     */
    private String name;

    /**
     * 物品封面
     */
    private String cover;

    /**
     * 具体位置
     */
    private String place;

    /**
     * 是否允许共享查看
     */
    private String isShared;

    /**
     * 是否可编辑
     */
    private Integer isEdit;

    /**
     * 是否可删除
     */
    private Integer isDelete;

    /**
     * 创建人id
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


    public WxWarehouseItems() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPidName() {
        return pidName;
    }

    public void setPidName(String pidName) {
        this.pidName = pidName;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getIsShared() {
        return isShared;
    }

    public void setIsShared(String isShared) {
        this.isShared = isShared;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(Integer isEdit) {
        this.isEdit = isEdit;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
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

    public void setChild(List<WxWarehouseItems> children) {
    }

    public Collection<WxWarehouseItems> getChild() {
        return null;
    }
}
