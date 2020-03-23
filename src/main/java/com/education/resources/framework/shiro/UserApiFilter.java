package com.education.resources.framework.shiro;

import com.education.resources.config.auth.AuthResourceConfig;
import com.education.resources.util.ServletUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class UserApiFilter extends BasicHttpAuthenticationFilter {

    protected boolean  isAnonUrl(ServletRequest request){
        String method = WebUtils.toHttp(request).getMethod().toUpperCase();
        return AuthResourceConfig.anonUrls.stream().anyMatch(urlItem -> pathsMatch(urlItem.getUrl(),request) && method.equals(urlItem.getMethod()));
    }


    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if(isAnonUrl(request)){
            return true;
        }
        if (StringUtils.isEmpty(((HttpServletRequest)request).getHeader("accessToken"))){
            try {
                ServletUtil.needLogin(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
        if (WebUtils.toHttp(request).getRequestURI().startsWith("/api")){
            return true;
        };
        return super.isAccessAllowed(request, response, mappedValue);
    }
}


