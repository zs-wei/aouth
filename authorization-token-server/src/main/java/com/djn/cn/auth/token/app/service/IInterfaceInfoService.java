package com.djn.cn.auth.token.app.service;

import java.util.List;

import com.djn.cn.auth.token.app.entity.InterfaceInfo;
import com.djn.cn.auth.token.base.service.IBaseService;

/**
 * 
 * <b>类   名：</b>IInterfaceInfo<br/>
 * <b>类描述：</b>TODO<br/>
 * <b>创建人：</b>op.nie-dongjia<br/>
 * <b>创建时间：</b>2019年1月17日 下午9:32:48<br/>
 * <b>修改人：</b>op.nie-dongjia<br/>
 * <b>修改时间：</b>2019年1月17日 下午9:32:48<br/>
 * <b>修改备注：</b><br/>
 *
 * @version   1.0<br/>
 *
 */
public interface IInterfaceInfoService extends IBaseService<InterfaceInfo, String>{
    /**
     * 
     * insertInterfaceAppInfoRelation  添加一条接口应用关系
     *
     * @param appInfoId
     * @param interfaceInfoId
     * @return   
     * @since 1.0
     * @author op.nie-dongjia
     */
    int insertInterfaceAppInfoRelation(String appInfoId,String interfaceInfoId);
     /**
     * 通过appInfoKey 查询可以访问的 接口
     * listByAppInfoKey  TODO
     *
     * @param appInfoKey
     * @return   
     * @since 1.0
     * @author op.nie-dongjia
     */
    List<InterfaceInfo> listByAppInfoKey(String appInfoKey);
    /**
     * 
     * listByAppInfoId  通过appInfoId 查询可以访问的 接口
     *
     * @param appInfoId
     * @return   
     * @since 1.0
     * @author op.nie-dongjia
     */
    List<InterfaceInfo> listByAppInfoId(String appInfoId);
}
