package com.djn.cn.oauth.provider.ucenter.base.entity;

import java.util.Date;

/**
 * 
 * @ClassName UserInfo
 * @Description UCenter 用户信息实体  面向对象设计与组装
 * @author nie-dongjia
 * @date 2018年8月29日 下午8:58:53
 *
 */
public class UserInfo {
    private String id;
    private String userName;
    private Date createDate;
    public UserInfo() {
        super();
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    @Override
    public String toString() {
        return "UserInfo [id=" + id + ", userName=" + userName + ", createDate=" + createDate + "]";
    } 
}
