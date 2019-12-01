package com.lss.education_resources_service.framework.shiro;


import com.lss.education_resources_service.bean.entity.user.User;
import com.lss.education_resources_service.exception.APIError;
import com.lss.education_resources_service.framework.redis.RedisCache;
import com.lss.education_resources_service.service.user.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


/**
 * 自定义实现 ShiroRealm，包含认证和授权两大模块
 *
 * @author MrBird
 */
@Component("authorizer")
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    @Autowired
    RedisCache redisCache;


    public ShiroRealm() {
        setCredentialsMatcher(new CredentialsMatcher() {
            @Override
            public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
                return true;
            }
        });
    }


    @Override
    public boolean supports(AuthenticationToken token) {

        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        if (authenticationToken instanceof JwtToken){
            JwtToken token = (JwtToken) authenticationToken;
            User user = userService.getUser(token.getToken());
            if (user == null) {
                APIError.NEED_LOGIN();
            } else {
                redisCache.setCacheObject(token.getToken(), user, 1800, TimeUnit.SECONDS);
                return new SimpleAuthenticationInfo(token, token, getName());
            }
        }
        APIError.NEED_LOGIN();
        return null;
    }

}
