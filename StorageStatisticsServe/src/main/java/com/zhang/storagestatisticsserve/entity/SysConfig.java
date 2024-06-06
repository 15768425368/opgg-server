package com.zhang.storagestatisticsserve.entity;

import java.util.Date;

public class SysConfig {
    private static final long serialVersionUID = 1L;

    /**
     * 系统配置表id
     */
    private Integer id;

    /**
     * 小程序/app版本号
     */
    private String version;

    /**
     * 状态：0正常、1停止服务、2预告停止服务
     */
    private Integer state;

    /**
     * 预告停止服务时间
     */
    private Date previewTime;

    /**
     * 停止服务原因：字段state 1、2公用
     */
    private String stopReason;

    /**
     * 版本号与客户端不一致时是否强制更新
     */
    private Integer forceUpdate;

    /**
     * 版本更新提示
     */
    private String updateTips;

    /**
     * 关于我们
     */
    private String aboutUs;

    /**
     * 客服类型方式
     */
    private String customerService;

    /**
     * 版本更新客户端下载url
     */
    private String updateUrl;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


    public SysConfig() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getPreviewTime() {
        return previewTime;
    }

    public void setPreviewTime(Date previewTime) {
        this.previewTime = previewTime;
    }

    public String getStopReason() {
        return stopReason;
    }

    public void setStopReason(String stopReason) {
        this.stopReason = stopReason;
    }

    public Integer getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(Integer forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public String getUpdateTips() {
        return updateTips;
    }

    public void setUpdateTips(String updateTips) {
        this.updateTips = updateTips;
    }

    public String getAboutUs() {
        return aboutUs;
    }

    public void setAboutUs(String aboutUs) {
        this.aboutUs = aboutUs;
    }

    public String getCustomerService() {
        return customerService;
    }

    public void setCustomerService(String customerService) {
        this.customerService = customerService;
    }

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
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
