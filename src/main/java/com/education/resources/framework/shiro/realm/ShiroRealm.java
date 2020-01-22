package com.education.resources.framework.shiro.realm;


import com.education.resources.framework.redis.RedisCache;
import com.lss.auth.config.ManagerRealm;
import com.education.resources.framework.shiro.JwtToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 自定义实现 ShiroRealm，包含认证和授权两大模块
 *
 * @author MrBird
 */
@Component("authorizer")
public class ShiroRealm extends ManagerRealm {

    //@Autowired
    //UserService userService;

    @Autowired
    RedisCache redisCache;


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
//            User user = userService.getUser(token.getToken());
//            if (user == null) {
//                APIError.NEED_LOGIN();
//            } else {
//                redisCache.setCacheObject(token.getToken(), user, 1800, TimeUnit.SECONDS);
//                return new SimpleAuthenticationInfo(token, token, getName());
//            }
        }
        return super.doGetAuthenticationInfo(authenticationToken);
    }

}
