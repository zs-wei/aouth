package com.djn.cn.auth.token.base.dao;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * <b>类   名：</b>IBaseDAO<br/>
 * <b>类描述：</b> DAO-公共通用方法<br/>
 * <b>创建人：</b>op.nie-dongjia<br/>
 * <b>创建时间：</b>2018年12月30日 上午1:07:37<br/>
 * <b>修改人：</b>op.nie-dongjia<br/>
 * <b>修改时间：</b>2018年12月30日 上午1:07:37<br/>
 * <b>修改备注：</b><br/>
 * @param <T>  类型 
 * @param <PK> 主键
 *
 * @version   1.0<br/>
 *
 */
public interface IBaseDAO <T, PK extends Serializable>{
	/**
	 * 
	 * add  添加
	 *
	 * @param obj
	 * @return    int 
	 * @since 1.0
	 * @author op.nie-dongjia
	 */
	int add(T obj);
	T find(PK id);
	/**
	 * 删除
	 * @Title  delete  
	 * @return int   
	 *
	 */
	int delete(T obj);
	/**
	 * 查询所有
	 * @Title  update  
	 * @return void   
	 *
	 */
	void update(T obj);
	/**
	 * 查询所有
	 * @Title  listAll  
	 * @return List<T>   
	 *
	 */
	List<T> listAll();
	/**
	 * 批量查询
	 * @Title  listBatch  
	 * @return List<T>   
	 *
	 */
	List<T> listBatch(List<PK> pks);
}
