package com.djn.cn.auth.token.oauth;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;
/**
 * 
 * <b>类   名：</b>RedisTokenStoreImpl<br/>
 * <b>类描述：</b>RedisTokenStoreImpl <br/>
 * <b>创建人：</b>op.nie-dongjia<br/>
 * <b>创建时间：</b>2019年3月24日 下午12:01:51<br/>
 * <b>修改人：</b>op.nie-dongjia<br/>
 * <b>修改时间：</b>2019年3月24日 下午12:01:51<br/>
 * <b>修改备注：</b><br/>
 *
 * @version   1.0<br/>
 *
 */
public class RedisTokenStoreImpl implements TokenStore {
    private static final Logger logger = LoggerFactory.getLogger(RedisTokenStoreImpl.class);
    private JedisPool jedisPool;
    private static final byte[] ACCESS_TOKEN_KEY = toBytes("access_token_key");
    private static final byte[] ACCESS_TOKEN_AUTH_KEY = toBytes("access_token_auth_key");
    private static final byte[] ACCESS_REFRESH_CODE_KEY = toBytes("access_refresh_code_key");
    private static final byte[] ACCESS_AUTH_ID_KEY = toBytes("access_auth_id_key");
    private static final byte[] REFRESH_TOKEN_KEY = toBytes("refresh_token_key");
    private static final byte[] REFRESH_TOKEN_AUTH_KEY = toBytes("refresh_token_auth_key");
    private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();

    public RedisTokenStoreImpl(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        logger.debug("readAuthentication token: {}", token);
        return readAuthentication(token.getValue());
    }

    @Override
    public OAuth2Authentication readAuthentication(String tokenValue) {
        logger.debug("readAuthentication tokenValue: {}", tokenValue);
        OAuth2Authentication authentication = null;
        String tokenKey = extractTokenKey(tokenValue);
        byte[] values = null;
        Jedis jedis = this.jedisPool.getResource();
        try {
            values = jedis.hget(ACCESS_TOKEN_AUTH_KEY, toBytes(tokenKey));
        } finally {
            jedis.close();
        }
        try {
            if (values != null) {
                authentication = deserializeAuthentication(values);
            } else if (logger.isInfoEnabled()) {
                logger.info("Failed to find access token for token " + tokenValue);
            }
        } catch (IllegalArgumentException e) {
            logger.warn("Failed to deserialize authentication for " + tokenValue, e);
            removeAccessToken(tokenValue);
        }
        return authentication;
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        logger.debug("storeAccessToken, token:{}, authentication: {}", token, authentication);

        String tokenKey = extractTokenKey(token.getValue());
        OAuth2AccessToken oldToken = readAccessToken(token.getValue());
        String oldRefreshToken = null;
        String oldAuthenticationKey = null;
        if (oldToken != null) {
            OAuth2Authentication oldAuthentication = readAuthentication(oldToken.getValue());
            if (((authentication != null) && (oldAuthentication != null) && (authentication.equals(oldAuthentication)))
                    || ((authentication == null) && (oldAuthentication == null))) {
                if (token.equals(oldToken)) {
                    return;
                }
            }
            if ((oldToken.getRefreshToken() != null) && (oldToken.getRefreshToken().getValue() != null)) {
                oldRefreshToken = oldToken.getRefreshToken().getValue();
            }
            if (oldAuthentication != null) {
                oldAuthenticationKey = this.authenticationKeyGenerator.extractKey(oldAuthentication);
            }
        }
        Jedis jedis = this.jedisPool.getResource();
        try {
            Transaction tx = jedis.multi();
            tx.hset(ACCESS_TOKEN_KEY, toBytes(tokenKey), serializeAccessToken(token));
            tx.hset(ACCESS_TOKEN_AUTH_KEY, toBytes(tokenKey), serializeAuthentication(authentication));
            if ((token.getRefreshToken() != null) && (token.getRefreshToken().getValue() != null)) {
                String refreshToken = token.getRefreshToken().getValue();
                tx.hset(ACCESS_REFRESH_CODE_KEY, toBytes(refreshToken), toBytes(token.getValue()));
                if ((oldRefreshToken != null) && (!oldRefreshToken.equals(refreshToken))) {
                    tx.hdel(ACCESS_REFRESH_CODE_KEY, new byte[][] { toBytes(oldRefreshToken) });
                }
            }
            String authenticationKey = this.authenticationKeyGenerator.extractKey(authentication);
            tx.hset(ACCESS_AUTH_ID_KEY, toBytes(authenticationKey), toBytes(token.getValue()));
            if ((oldAuthenticationKey != null) && (!oldAuthenticationKey.equals(authenticationKey))) {
                tx.hdel(ACCESS_AUTH_ID_KEY, new byte[][] { toBytes(oldAuthenticationKey) });
            }
            tx.exec();
        } finally {
            jedis.close();
        }
    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        logger.debug("readAccessToken, tokenValue: {}", tokenValue);
        OAuth2AccessToken accessToken = null;

        String tokenKey = extractTokenKey(tokenValue);
        Jedis jedis = this.jedisPool.getResource();
        byte[] values = null;
        try {
            values = jedis.hget(ACCESS_TOKEN_KEY, toBytes(tokenKey));
        } catch (Exception ex) {
            logger.error("readAccessToken exception:", ex);
        } finally {
            jedis.close();
        }
        try {
            if (values != null) {
                accessToken = deserializeAccessToken(values);
            } else if (logger.isInfoEnabled()) {
                logger.info("Failed to find access token for token " + tokenValue);
            }
        } catch (IllegalArgumentException e) {
            logger.warn("Failed to deserialize access token for token " + tokenValue, e);
            removeAccessToken(tokenValue);
        }
        return accessToken;
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken token) {
        logger.debug("removeAccessToken, token: {}", token);
        removeAccessToken(token.getValue());
    }

    private void removeAccessToken(String tokenValue) {
        String tokenKey = extractTokenKey(tokenValue);
        OAuth2AccessToken token = readAccessToken(tokenValue);
        OAuth2Authentication authentication = readAuthentication(tokenValue);

        logger.debug("removeAccessToken , tokenKey:{}, tokenValue: {}, authentication:{}",
                new Object[] { tokenKey, tokenValue, authentication });

        Jedis jedis = this.jedisPool.getResource();
        try {
            Transaction tx = jedis.multi();
            tx.hdel(ACCESS_TOKEN_KEY, new byte[][] { toBytes(tokenKey) });
            tx.hdel(ACCESS_TOKEN_AUTH_KEY, new byte[][] { toBytes(tokenKey) });
            if ((token != null) && (token.getRefreshToken() != null) && (token.getRefreshToken().getValue() != null)) {
                String refreshToken = token.getRefreshToken().getValue();
                tx.hdel(ACCESS_REFRESH_CODE_KEY, new byte[][] { toBytes(refreshToken) });
            }
            if (authentication != null) {
                tx.hdel(ACCESS_AUTH_ID_KEY,
                        new byte[][] { toBytes(this.authenticationKeyGenerator.extractKey(authentication)) });
            }
            tx.exec();
        } catch (Exception ex) {
            logger.error("removeAccessToken exception:", ex);
        } finally {
            jedis.close();
        }
    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        String refreshTokenKey = extractTokenKey(refreshToken.getValue());
        Jedis jedis = this.jedisPool.getResource();
        try {
            Transaction tx = jedis.multi();
            tx.hset(REFRESH_TOKEN_KEY, toBytes(refreshTokenKey), serializeRefreshToken(refreshToken));
            tx.hset(REFRESH_TOKEN_AUTH_KEY, toBytes(refreshTokenKey), serializeAuthentication(authentication));
            tx.exec();
        } finally {
            jedis.close();
        }
    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        OAuth2RefreshToken refreshToken = null;

        String tokenKey = extractTokenKey(tokenValue);
        Jedis jedis = this.jedisPool.getResource();
        try {
            byte[] values = jedis.hget(REFRESH_TOKEN_KEY, toBytes(tokenKey));
            if (values != null) {
                refreshToken = deserializeRefreshToken(values);
            } else if (logger.isInfoEnabled()) {
                logger.info("Failed to find refresh token for token " + tokenValue);
            }
        } catch (IllegalArgumentException e) {
            logger.warn("Failed to deserialize refresh token for token " + tokenValue, e);
            removeAccessToken(tokenValue);
        } finally {
            jedis.close();
        }
        return refreshToken;
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        return readAuthenticationForRefreshToken(token.getValue());
    }

    public OAuth2Authentication readAuthenticationForRefreshToken(String tokenValue) {
        OAuth2Authentication authentication = null;

        String tokenKey = extractTokenKey(tokenValue);
        Jedis jedis = this.jedisPool.getResource();
        try {
            byte[] values = jedis.hget(REFRESH_TOKEN_AUTH_KEY, toBytes(tokenKey));
            if (values != null) {
                authentication = deserializeAuthentication(values);
            } else if (logger.isInfoEnabled()) {
                logger.info("Failed to find access token for token " + tokenValue);
            }
        } catch (IllegalArgumentException e) {
            logger.warn("Failed to deserialize access token for token " + tokenValue, e);
            removeAccessToken(tokenValue);
        } finally {
            jedis.close();
        }
        return authentication;
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken token) {
        removeRefreshToken(token.getValue());
    }

    public void removeRefreshToken(String tokenValue) {
        String tokenKey = extractTokenKey(tokenValue);
        Jedis jedis = this.jedisPool.getResource();
        try {
            jedis.hdel(REFRESH_TOKEN_KEY, new byte[][] { toBytes(tokenKey) });
            jedis.hdel(REFRESH_TOKEN_AUTH_KEY, new byte[][] { toBytes(tokenKey) });
        } finally {
            jedis.close();
        }
    }

    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
        removeAccessTokenUsingRefreshToken(refreshToken.getValue());
    }

    public void removeAccessTokenUsingRefreshToken(String refreshToken) {
        Jedis jedis = this.jedisPool.getResource();
        try {
            byte[] tokenValue = jedis.hget(ACCESS_REFRESH_CODE_KEY, toBytes(refreshToken));
            removeAccessToken(new String(tokenValue, Charset.forName("UTF-8")));
        } finally {
            jedis.close();
        }
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        logger.debug("getAccessToken , authentication:{} ", authentication);
        OAuth2AccessToken accessToken = null;
        String key = this.authenticationKeyGenerator.extractKey(authentication);
        Jedis jedis = this.jedisPool.getResource();
        try {
            byte[] tokenValue = jedis.hget(ACCESS_AUTH_ID_KEY, toBytes(key));
            if (tokenValue != null) {
                accessToken = readAccessToken(new String(tokenValue, Charset.forName("UTF-8")));
                if (accessToken.isExpired()) {
                    removeAccessToken(accessToken.getValue());
                }
            }
        } finally {
            jedis.close();
        }
        if (accessToken != null) {
            OAuth2Authentication authenticationStored = readAuthentication(accessToken.getValue());
            if (authenticationStored != null) {
                String authenticationKey = this.authenticationKeyGenerator.extractKey(authenticationStored);
                logger.debug("getAccessToken , authenticationStored:{}, authenticationKey:{} ", authenticationStored,
                        authenticationKey);
                if (!key.equals(authenticationKey)) {
                    storeAccessToken(accessToken, authentication);
                }
            }
        }
        return accessToken;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        throw new UnsupportedOperationException();
    }

    private static byte[] toBytes(String value) {
        return value.getBytes(Charset.forName("UTF-8"));
    }

    protected byte[] serializeAccessToken(OAuth2AccessToken token) {
        return SerializationUtils.serialize(token);
    }

    protected byte[] serializeRefreshToken(OAuth2RefreshToken token) {
        return SerializationUtils.serialize(token);
    }

    protected byte[] serializeAuthentication(OAuth2Authentication authentication) {
        return SerializationUtils.serialize(authentication);
    }

    protected OAuth2AccessToken deserializeAccessToken(byte[] token) {
        return (OAuth2AccessToken) SerializationUtils.deserialize(token);
    }

    protected OAuth2RefreshToken deserializeRefreshToken(byte[] token) {
        return (OAuth2RefreshToken) SerializationUtils.deserialize(token);
    }

    protected OAuth2Authentication deserializeAuthentication(byte[] authentication) {
        return (OAuth2Authentication) SerializationUtils.deserialize(authentication);
    }

    protected String extractTokenKey(String tokenValue) {
        if (tokenValue == null) {
            return null;
        }
        try {
            MessageDigest digest;
            digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(tokenValue.getBytes("UTF-8"));
            return String.format("%032x", new Object[] { new BigInteger(1, bytes) });

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
        }

        catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");
        }
    }
}
