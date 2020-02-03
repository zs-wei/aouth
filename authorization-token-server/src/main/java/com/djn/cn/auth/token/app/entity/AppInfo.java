package com.djn.cn.auth.token.app.entity;

import java.util.Date;

/**
 * 
 * <b>类   名：</b>AppInfo<br/>
 * <b>类描述：</b>平台-应用信息实体<br/>
 * <b>创建人：</b>op.nie-dongjia<br/>
 * <b>创建时间：</b>2018年12月30日 下午3:52:11<br/>
 * <b>修改人：</b>op.nie-dongjia<br/>
 * <b>修改时间：</b>2018年12月30日 下午3:52:11<br/>
 * <b>修改备注：</b><br/>
 *
 * @version   1.0<br/>
 *
 */
public class AppInfo extends BaseEntity{
	private static final long serialVersionUID = 1L;
	/**应用key*/
	private String key;
	/**应用secret*/
	private String secret;
	/**是否隐藏*/
	private boolean hidden;
	/**应用全称*/
	private String fullName;
	/**应用简称*/
	private String shortName;
	/**系统基础应用 1  平台应用2  第三方应用 3  */
	private Integer type;
	/**应用状态，10-初始化；0-待审核；1-审核通过；2-审核未通过；3-申请上架；4-已上架；5-申请下架；6-已下架；7-应用维护中；8-黑名单应用;9-已删除 */
	private Integer state;
	/**展示地址*/
	private String showUrl;
	/**回调地址*/
	private String callbackAddrUrl;
	/**发布时间*/
	private Date publishDate; 
	/**应用来源(0:开发者发布；1:管理者发布)*/
	private Integer origin;
	/**图标*/
	private String icon;
	/**版本*/
	private String version;
	/**应用类型Id*/
	private String appCategoryId;
	/**是否推荐*/
	private boolean recommend;
	/**是否免费*/
	private boolean free;
	
	public AppInfo() {
		super();
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	
	public boolean isHidden() {
		return hidden;
	}
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getShowUrl() {
		return showUrl;
	}
	public void setShowUrl(String showUrl) {
		this.showUrl = showUrl;
	}
	public Integer getOrigin() {
		return origin;
	}
	public void setOrigin(Integer origin) {
		this.origin = origin;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getAppCategoryId() {
		return appCategoryId;
	}
	public void setAppCategoryId(String appCategoryId) {
		this.appCategoryId = appCategoryId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getCallbackAddrUrl() {
		return callbackAddrUrl;
	}

	public void setCallbackAddrUrl(String callbackAddrUrl) {
		this.callbackAddrUrl = callbackAddrUrl;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}



	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public boolean isRecommend() {
		return recommend;
	}

	public void setRecommend(boolean recommend) {
		this.recommend = recommend;
	}

	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}

	@Override
	public String toString() {
		return "AppInfo [key=" + key + ", secret=" + secret + ", hidden=" + hidden + ", fullName=" + fullName
				+ ", shortName=" + shortName + ", type=" + type + ", state=" + state + ", showUrl=" + showUrl
				+ ", callbackAddrUrl=" + callbackAddrUrl + ", publishDate=" + publishDate + ", origin=" + origin
				+ ", icon=" + icon + ", version=" + version + ", appCategoryId=" + appCategoryId + ", recommend="
				+ recommend + ", free=" + free + "]";
	}
}
