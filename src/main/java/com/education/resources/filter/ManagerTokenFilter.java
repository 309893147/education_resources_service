package com.education.resources.filter;

import com.education.resources.bean.auth.ManagerToken;
import com.education.resources.bean.auth.Permission;
import com.education.resources.config.auth.AuthResourceConfig;
import com.education.resources.service.auth.PermissionService;
import com.education.resources.util.ServletUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Component;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class ManagerTokenFilter extends BasicHttpAuthenticationFilter {

    private PermissionService permissionService;

    public ManagerTokenFilter(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    protected boolean  isAnonUrl(ServletRequest request){
        String method = WebUtils.toHttp(request).getMethod().toUpperCase();
        return AuthResourceConfig.anonUrls.stream().anyMatch(urlItem -> pathsMatch(urlItem.getUrl(),request) && method.equals(urlItem.getMethod()));
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if(isAnonUrl(request)){
            return true;
        }
        if(!SecurityUtils.getSubject().isAuthenticated()){
            if (!tryLogin(request,response)){
                try {
                    ServletUtil.needLogin(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }
        }
        //api 部分只要登录就行
        if (WebUtils.toHttp(request).getRequestURI().startsWith("/api")){
            return true;
        };
        String url = getPathWithinApplication(request);
        String method = WebUtils.toHttp(request).getMethod().toUpperCase();
        if (AuthResourceConfig.permissions.indexOf(new Permission(method,url)) ==-1){
            return true;
        }
        if (!permissionService.hasPermission(method,url)){
            try {
                ServletUtil.accessDeny(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
        return true;
    }

    protected boolean  tryLogin(ServletRequest request, ServletResponse response){
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader("accessToken");
        ManagerToken jwtToken = new ManagerToken(token);
        try {
            getSubject(request, response).login(jwtToken);
            return true;
        }catch (Exception e){

        }
        return false;

    }
}
