package com.djn.cn.oauth.provider.ucenter.base.service;

import com.djn.cn.oauth.provider.ucenter.base.entity.UserInfo;

public interface IUserInfoService {
    /**
     * 通过id 查询 ucenter 用户详情
     * @Title  findById  
     * @return UserInfo   
     *
     */
    UserInfo findById(String id);
}
