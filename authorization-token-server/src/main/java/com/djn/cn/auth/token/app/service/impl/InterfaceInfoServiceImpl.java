package com.djn.cn.auth.token.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djn.cn.auth.token.app.dao.IInterfaceInfoDAO;
import com.djn.cn.auth.token.app.entity.InterfaceInfo;
import com.djn.cn.auth.token.app.service.IInterfaceInfoService;
import com.djn.cn.auth.token.base.service.impl.BaseServiceImpl;
@Service
public class InterfaceInfoServiceImpl extends BaseServiceImpl<InterfaceInfo,String>  implements IInterfaceInfoService{
    @Autowired
    private IInterfaceInfoDAO iInterfaceInfoDAO;

    @Override
    public int insertInterfaceAppInfoRelation(String appInfoId, String interfaceInfoId) {
        return iInterfaceInfoDAO.insertInterfaceAppInfoRelation(appInfoId, interfaceInfoId);
    }

    @Override
    public List<InterfaceInfo> listByAppInfoKey(String appInfoKey) {
        return iInterfaceInfoDAO.findByAppInfoKey(appInfoKey);
    }


    @Override
    public List<InterfaceInfo> listByAppInfoId(String appInfoId) {
        return iInterfaceInfoDAO.findByAppInfoId(appInfoId);
    }
}
