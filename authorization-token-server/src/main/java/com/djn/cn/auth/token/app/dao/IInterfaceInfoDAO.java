package com.djn.cn.auth.token.app.dao;



import java.util.List;
import java.util.Map;

import com.djn.cn.auth.token.app.dto.InterfaceDto;
import com.djn.cn.auth.token.app.entity.InterfaceInfo;
import com.djn.cn.auth.token.base.dao.IBaseDAO;



/**
 * 
 * <b>类   名：</b>IInterfaceInfoDAO<br/>
 * <b>类描述：</b>DAO<br/>
 * <b>创建人：</b>op.nie-dongjia<br/>
 * <b>创建时间：</b>2019年1月13日 下午4:24:27<br/>
 * <b>修改人：</b>op.nie-dongjia<br/>
 * <b>修改时间：</b>2019年1月13日 下午4:24:27<br/>
 * <b>修改备注：</b><br/>
 *
 * @version   1.0<br/>
 *
 */
public interface IInterfaceInfoDAO extends IBaseDAO<InterfaceInfo, String>{
	/**
	 * 
	 * insertUserRoleRelation  添加接口应用关系
	 *
	 * @param appInfoId
	 * @param interfaceInfoId
	 * @return   
	 * @since 1.0
	 * @author op.nie-dongjia
	 */
	int insertInterfaceAppInfoRelation(String appInfoId,String interfaceInfoId);
	/**
	 * 
	 * deleteInterfaceAppInfoRelation  通过appInfoId 删除应用全选接口
	 *
	 * @param appInfoId
	 * @return   
	 * @since 1.0
	 * @author op.nie-dongjia
	 */
	int deleteInterfaceAppInfoRelation(String appInfoId);
	/**
	 * 
	 * listByAppInfoKey  appInfoKey 所拥有接口
	 *
	 * @param appInfoKey
	 * @return   
	 * @since 1.0
	 * @author op.nie-dongjia
	 */
	List<InterfaceInfo> findByAppInfoKey(String appInfoKey);
	List<InterfaceInfo> findByAppInfoId(String appInfoId);
	List<InterfaceDto> queryByName(Map<String, String> paramMap);
	
}
