package com.zhang.storagestatisticsserve.entity;

import java.util.Date;

/**
 * @description wx_user
 * @author BEJSON
 * @date 2024-04-09
 */
public class WxUser {

    private static final long serialVersionUID = 1L;

    /**
     * 用户系统ID
     */
    private Integer id;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户微信openid
     */
    private String openid;

    /**
     * 用户性别：0未知、1男、2女
     */
    private Integer gender;

    /**
     * 用户生日
     */
    private Date birthday;

    /**
     * 用户手机号
     */
    private String phoneNumber;

    /**
     * 状态：0正常、1停用
     */
    private Integer state;

    /**
     * ip
     */
    private String ip;

    /**
     * 设备型号
     */
    private String deviceModel;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


    public WxUser() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
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