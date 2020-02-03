package com.djn.cn.auth.token.app.entity;
/**
 * 
 * <b>类   名：</b>InetrfaceInfo<br/>
 * <b>类描述：</b>接口实体<br/>
 * <b>创建人：</b>op.nie-dongjia<br/>
 * <b>创建时间：</b>2019年1月13日 下午3:47:59<br/>
 * <b>修改人：</b>op.nie-dongjia<br/>
 * <b>修改时间：</b>2019年1月13日 下午3:47:59<br/>
 * <b>修改备注：</b><br/>
 *
 * @version   1.0<br/>
 *
 */
public class InterfaceInfo extends BaseEntity{
	private static final long serialVersionUID = 1L;
	/**接口所属服务Id*/
	private String serviceId;
	/**状态*/
	private Integer state;
	/**访问接口的url*/
	private String url;
	/**类型 0:base*/
	private Integer type;

	public InterfaceInfo() {
		super();
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "InetrfaceInfo [serviceId=" + serviceId + ", state=" + state + ", url=" + url + ", type=" + type + "]";
	}
}
