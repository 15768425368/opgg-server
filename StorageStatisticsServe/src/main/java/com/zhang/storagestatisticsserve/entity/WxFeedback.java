package com.zhang.storagestatisticsserve.entity;

import java.util.Date;

public class WxFeedback {
    private static final long serialVersionUID = 1L;

    /**
     * 反馈问题id
     */
    private Integer id;

    /**
     * 问题内容
     */
    private String content;

    /**
     * 反馈人openid
     */
    private String openid;

    /**
     * 反馈人联系方式
     */
    private String phone;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date uploadTime;


    public WxFeedback() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

}
