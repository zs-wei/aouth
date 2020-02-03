package com.djn.cn.auth.token.base.entity;

import java.io.Serializable;

/**
 * 
 * <b>类   名：</b>TokenInfo<br/>
 * <b>类描述：</b>TokenInfo 信息<br/>
 * <b>创建人：</b>op.nie-dongjia<br/>
 * <b>创建时间：</b>2019年3月24日 下午3:24:49<br/>
 * <b>修改人：</b>op.nie-dongjia<br/>
 * <b>修改时间：</b>2019年3月24日 下午3:24:49<br/>
 * <b>修改备注：</b><br/>
 *
 * @version   1.0<br/>
 *
 */
public class TokenInfo implements Serializable{
    private static final long serialVersionUID = 1L;
    /**访问令牌*/
    private String access_token;
    /**令牌类型 可以是bearer类型或mac类型*/
    private String token_type;
    /**过期时间*/
    private long   expires_in;
    /**更新令牌，用来获取下一次的访问令牌*/
    private String   refresh_token;
    /**权限范围*/
    private String   scope;
    public TokenInfo() {
        super();
        // TODO Auto-generated constructor stub
    }
    public String getAccess_token() {
        return access_token;
    }
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
    public String getToken_type() {
        return token_type;
    }
    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }
    public long getExpires_in() {
        return expires_in;
    }
    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }
    public String getScope() {
        return scope;
    }
    public void setScope(String scope) {
        this.scope = scope;
    }
    public String getRefresh_token() {
        return refresh_token;
    }
    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
    @Override
    public String toString() {
        return "TokenInfo [access_token=" + access_token + ", token_type=" + token_type + ", expires_in=" + expires_in
                + ", refresh_token=" + refresh_token + ", scope=" + scope + "]";
    }
}
