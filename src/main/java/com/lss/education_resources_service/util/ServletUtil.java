package com.lss.education_resources_service.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lss.education_resources_service.exception.APIError;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void needLogin(ServletResponse servletResponse) throws IOException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.addHeader("Content-Type","application/json;charset=utf-8");
        servletResponse.getWriter().write(objectMapper.writeValueAsString(APIError.error(401,"需要登录")));
        servletResponse.flushBuffer();
    }

    public static void missHeader(ServletResponse servletResponse) throws IOException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.addHeader("Content-Type","application/json;charset=utf-8");
        servletResponse.getWriter().write(objectMapper.writeValueAsString(APIError.error(401,"缺少token参数")));
        servletResponse.flushBuffer();
    }
}
