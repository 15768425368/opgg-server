package com.zhang.storagestatisticsserve.entity;

import java.util.Date;

public class PromoteApplication {
    private static final long serialVersionUID = 1L;

    /**
     * 外部推广应用id
     */
    private Integer id;

    /**
     * 外部小程序appid
     */
    private String wxAppid;

    /**
     * 外部小程序原始id
     */
    private String wxOriginalId;

    /**
     * 外部应用path路径
     */
    private String path;

    /**
     * 外部小程序二维码
     */
    private String ercode;

    /**
     * 推广标题
     */
    private String title;

    /**
     * 推广封面
     */
    private String cover;

    /**
     * 状态：0启用、1停用
     */
    private Integer state;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


    public PromoteApplication() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWxAppid() {
        return wxAppid;
    }

    public void setWxAppid(String wxAppid) {
        this.wxAppid = wxAppid;
    }

    public String getWxOriginalId() {
        return wxOriginalId;
    }

    public void setWxOriginalId(String wxOriginalId) {
        this.wxOriginalId = wxOriginalId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getErcode() {
        return ercode;
    }

    public void setErcode(String ercode) {
        this.ercode = ercode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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
