package com.djn.cn.oauth.provider.ucenter.base.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.djn.cn.oauth.provider.ucenter.base.entity.UserInfo;
import com.djn.cn.oauth.provider.ucenter.base.service.IUserInfoService;
@Service
public class UserInfoServiceImpl implements IUserInfoService{
    @Override
    public UserInfo findById(String id) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo.setUserName("聂冬佳");
        userInfo.setCreateDate(new Date());
        return userInfo;
    }

}
