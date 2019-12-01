package com.lss.education_resources_service.framework.shiro;

import com.lss.education_resources_service.framework.config.ResourcesConfig;

import com.lss.education_resources_service.util.LoggerUtils;
import com.lss.education_resources_service.util.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class JWTFilter extends BasicHttpAuthenticationFilter {

    private static final String TOKEN = "token";

    @Override
    protected boolean isLoginAttempt(String authzHeader) {
        return super.isLoginAttempt(authzHeader);
    }

    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {

        String method = WebUtils.toHttp(request).getMethod().toUpperCase();
        return ResourcesConfig.anonUrls.stream().anyMatch(urlItem -> pathsMatch(urlItem.getUrl(), request) && method.equals(urlItem.getMethod()));
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {
            return true;
        }
        try {
            return executeLogin(request, response);
        } catch (Exception e) {
            try {
                LoggerUtils.log.debug(e.getMessage());
                ServletUtil.needLogin(response);
                return false;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader(TOKEN);
        if (StringUtils.isEmpty(token)) {
            ServletUtil.missHeader(response);
            return false;
        }
        JwtToken jwtToken = new JwtToken(token);
        Subject subject = getSubject(request, response);
        subject.login(jwtToken);
        return true;
    }
}
