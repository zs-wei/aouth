package com.djn.cn.auth.token.app.dto;

import java.util.Date;

public class AppFlowDto {
    private String appId;
    private String interfaceName;
    private Integer resourceService;
    private Integer sum;
    private Integer dailySum;
    private Date createTime;
    private Date updateTime;
    public AppFlowDto() {
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
    public Integer getResourceService() {
        return resourceService;
    }
    public void setResourceService(Integer resourceService) {
        this.resourceService = resourceService;
    }
    public Integer getSum() {
        return sum;
    }
    public void setSum(Integer sum) {
        this.sum = sum;
    }
    public Integer getDailySum() {
        return dailySum;
    }
    public void setDailySum(Integer dailySum) {
        this.dailySum = dailySum;
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
