package com.djn.cn.auth.token.app.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * <b>类   名：</b>BaseEntity<br/>
 * <b>类描述：</b>BaseEntity<br/>
 * <b>创建人：</b>op.nie-dongjia<br/>
 * <b>创建时间：</b>2019年1月17日 下午9:34:28<br/>
 * <b>修改人：</b>op.nie-dongjia<br/>
 * <b>修改时间：</b>2019年1月17日 下午9:34:28<br/>
 * <b>修改备注：</b><br/>
 *
 * @version   1.0<br/>
 *
 */
public abstract class BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/**id*/    
	private String id;
	/**名称*/
	private String name;
	/**创建日期*/
	private Date createTime;
	/**  最后修改日期 */ 
	private Date   lastUpdateTime;
    /** 描述 */
    private String description;
    /**创建者*/
	private String creatorId;
    /**最后修改者*/
    private String lastUpdateUserId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	public String getLastUpdateUserId() {
		return lastUpdateUserId;
	}
	public void setLastUpdateUserId(String lastUpdateUserId) {
		this.lastUpdateUserId = lastUpdateUserId;
	}
}
