package com.djn.cn.auth.token.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.djn.cn.auth.token.app.dto.AppBlackListDto;
import com.djn.cn.auth.token.app.dto.AppDto;
import com.djn.cn.auth.token.app.dto.AppFlowDto;

public interface ITokenAppDAO {
    List<AppDto> queryByAppkey(String appKey);
    List<AppBlackListDto> queryBlackListByAppKey(String appKey);
    AppFlowDto queryFlowMax(@Param("appKey") String appKey, @Param("serviceName") String serviceName, @Param("interfaceName") String interfaceName);
    String queryResourceServiceByName(String serviceName);
}
