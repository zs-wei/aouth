package com.djn.cn.auth.token.app.dao;



import java.util.List;

import com.djn.cn.auth.token.app.entity.AppInfo;
import com.djn.cn.auth.token.base.dao.IBaseDAO;



/**
 * 
 * <b>类   名：</b>IAppInfoDAO<br/>
 * <b>类描述：</b>服务<br/>
 * <b>创建人：</b>op.nie-dongjia<br/>
 * <b>创建时间：</b>2019年1月12日 下午6:11:00<br/>
 * <b>修改人：</b>op.nie-dongjia<br/>
 * <b>修改时间：</b>2019年1月12日 下午6:11:00<br/>
 * <b>修改备注：</b><br/>
 *
 * @version   1.0<br/>
 *
 */
public interface IAppInfoDAO extends IBaseDAO<AppInfo, String>{
	AppInfo findByKey(String key);
	List<AppInfo> listByCreatorId(String creatorId);
	List<AppInfo> listByState(Integer state);
}
