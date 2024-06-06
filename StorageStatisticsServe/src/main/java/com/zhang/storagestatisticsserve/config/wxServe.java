package com.zhang.storagestatisticsserve.config;

public class wxServe {
    private String appid;
    private String secret;

    public wxServe(){
        this.appid = "wx96fc9d2f357980b5";
        this.secret = "8d53484ed5ae23b8c28381a25dc70db5";
    }

    public String getAppid() {
        return appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
