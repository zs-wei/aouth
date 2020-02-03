package com.djn.cn.auth.token.oauth;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import com.djn.cn.auth.token.app.dao.IAppInfoDAO;
import com.djn.cn.auth.token.app.entity.AppInfo;
/**
 * 
 * <b>类   名：</b>GuestServiceImpl<br/>
 * <b>类描述：</b>provider-客户端配置<br/>
 * <b>创建人：</b>op.nie-dongjia<br/>
 * <b>创建时间：</b>2019年3月23日 下午6:50:47<br/>
 * <b>修改人：</b>op.nie-dongjia<br/>
 * <b>修改时间：</b>2019年3月23日 下午6:50:47<br/>
 * <b>修改备注：</b><br/>
 *
 * @version   1.0<br/>
 *
 */
public class GuestServiceImpl implements ClientDetailsService{
    private static final Logger log = LoggerFactory.getLogger(GuestServiceImpl.class);
    @Autowired
    private IAppInfoDAO iAppInfoDAO;
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
       
        if (StringUtils.isBlank(clientId))
        {
          log.error("loadClientByClientId, but id is null");
          throw new NoSuchClientException("No client recognized with id: " + clientId);
        }
        
        AppInfo appInfo = iAppInfoDAO.findByKey(clientId);
        if (appInfo == null)
        {
          log.error("loadClientByClientId, but data query from db is error");
          throw new NoSuchClientException("No client recognized with id: " + clientId);
        }
        List<String> authorizedGrantTypes = new ArrayList<>();
        authorizedGrantTypes.add("client_credentials");
        BaseClientDetails clientDetails = new BaseClientDetails();
        clientDetails.setClientId(clientId);
        clientDetails.setClientSecret(appInfo.getSecret());
        clientDetails.setAuthorizedGrantTypes(authorizedGrantTypes);
        Set<String> scopes = new HashSet<>();
        scopes.add("client_app");
        clientDetails.setScope(scopes);
        return clientDetails;
    }
}
