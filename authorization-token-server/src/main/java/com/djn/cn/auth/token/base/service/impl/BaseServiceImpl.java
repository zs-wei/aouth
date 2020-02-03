package com.djn.cn.auth.token.base.service.impl;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.djn.cn.auth.token.base.dao.IBaseDAO;
import com.djn.cn.auth.token.base.service.IBaseService;



/**
 * 
 * <b>类   名：</b>BaseServiceImpl<br/>
 * <b>类描述：</b>业务层通用方法-实现<br/>
 * <b>创建人：</b>op.nie-dongjia<br/>
 * <b>创建时间：</b>2018年12月30日 下午7:25:48<br/>
 * <b>修改人：</b>op.nie-dongjia<br/>
 * <b>修改时间：</b>2018年12月30日 下午7:25:48<br/>
 * <b>修改备注：</b><br/>
 * @param <T> 类型 
 * @param <PK> 主键
 *
 * @version   1.0<br/>
 *
 */
public class BaseServiceImpl<T,PK extends Serializable> implements IBaseService<T,PK>  {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private IBaseDAO<T, PK> iBaseDAO;
	@Override
	public int add(T obj) {
		return iBaseDAO.add(obj);
	}
	@Override
	public T find(PK id) {
		return iBaseDAO.find(id);
	}
	@Override
	public int delete(T obj) {
		return iBaseDAO.delete(obj);
	}
	@Override
	public void update(T obj) {
		iBaseDAO.update(obj);
	}
	@Override
	public List<T> listAll() {
		return iBaseDAO.listAll();
	}
}
