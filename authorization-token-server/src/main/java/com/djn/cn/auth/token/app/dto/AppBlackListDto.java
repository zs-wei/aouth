package com.djn.cn.auth.token.app.dto;

public class AppBlackListDto {
    private String appId;
    private String clientIp;
    public AppBlackListDto() {
        super();
        // TODO Auto-generated constructor stub
    }
    public String getAppId() {
        return appId;
    }
    public void setAppId(String appId) {
        this.appId = appId;
    }
    public String getClientIp() {
        return clientIp;
    }
    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }
}
