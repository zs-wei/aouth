package com.djn.cn.auth.token.app.dto;

public class AppInterfaceDto {
    private String appId;
    private String interfaceName;
    private Integer serviceId;
    private String platform;
    private String developerId;
    public AppInterfaceDto() {
        super();
        // TODO Auto-generated constructor stub
    }
    public String getAppId() {
        return appId;
    }
    public void setAppId(String appId) {
        this.appId = appId;
    }
    public String getInterfaceName() {
        return interfaceName;
    }
    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }
    public Integer getServiceId() {
        return serviceId;
    }
    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }
    public String getPlatform() {
        return platform;
    }
    public void setPlatform(String platform) {
        this.platform = platform;
    }
    public String getDeveloperId() {
        return developerId;
    }
    public void setDeveloperId(String developerId) {
        this.developerId = developerId;
    }
    
}
