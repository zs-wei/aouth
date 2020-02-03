package com.djn.cn.auth.token.base.service;

import java.io.Serializable;
import java.util.List;
/**
 * 
 * <b>类   名：</b>IBaseService<br/>
 * <b>类描述：</b>服务抽象通用方法<br/>
 * <b>创建人：</b>op.nie-dongjia<br/>
 * <b>创建时间：</b>2018年12月30日 下午7:24:59<br/>
 * <b>修改人：</b>op.nie-dongjia<br/>
 * <b>修改时间：</b>2018年12月30日 下午7:24:59<br/>
 * <b>修改备注：</b><br/>
 * @param <T>
 * @param <PK>
 *
 * @version   1.0<br/>
 *
 */
public interface IBaseService <T, PK extends Serializable>{
	int add(T obj);
	T find(PK id);
	int delete(T obj);
	void update(T obj);
	List<T> listAll();
}

