package com.education.resources.config.auth;

import com.education.resources.bean.auth.ManagerLoginLog;
import com.education.resources.bean.auth.ManagerToken;
import com.education.resources.service.auth.AuthManagerService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class ManagerRealm  extends AuthorizingRealm {

    AuthManagerService authManagerService;


    public ManagerRealm() {
        setCredentialsMatcher(new CredentialsMatcher() {
            @Override
            public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
                return true;
            }
        });
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof ManagerToken;
    }

    @Autowired
    public void setAuthManagerService(AuthManagerService authManagerService) {
        this.authManagerService = authManagerService;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if (authenticationToken instanceof ManagerToken){
            ManagerToken token = (ManagerToken) authenticationToken;
            ManagerLoginLog user = authManagerService.getManagerLoginLog(token.getToken());
            return new SimpleAuthenticationInfo(user,user.getToken(),getName());
        }
        return null;
    }
}
