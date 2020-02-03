package com.djn.cn.auth.token.app.dao;

import java.util.List;
import java.util.Map;

import com.djn.cn.auth.token.app.dto.AppInterfaceDto;

public interface IAppInterfaceDAO {
    List<AppInterfaceDto> queryByAll(Map<String, String> paramMap);
}
