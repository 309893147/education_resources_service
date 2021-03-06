package com.education.resources.framework.shiro.realm;


import com.education.resources.bean.auth.ManagerLoginLog;
import com.education.resources.bean.entity.user.User;
import com.education.resources.bean.vo.UserLoginVo;
import com.education.resources.config.auth.ManagerRealm;
import com.education.resources.framework.redis.RedisCache;
import com.education.resources.framework.shiro.JwtToken;
import com.education.resources.service.UserService;
import com.education.resources.service.auth.AuthManagerService;
import com.education.resources.util.rest.APIError;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
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
public class ShiroRealm extends ManagerRealm {

    @Autowired
    AuthManagerService authManagerService;

    @Autowired
    RedisCache redisCache;


    @Autowired
    UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {

        return token instanceof JwtToken || super.supports(token);
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
            User user = userService.getCurrentUser();
            if (user == null) {
                APIError.NEED_LOGIN();
            }
            return new SimpleAuthenticationInfo(user, token, this.getName());
        }
        return super.doGetAuthenticationInfo(authenticationToken);
    }

}
