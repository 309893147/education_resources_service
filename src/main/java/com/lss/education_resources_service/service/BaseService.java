package com.lss.education_resources_service.service;


import com.alibaba.fastjson.JSONObject;
import com.lss.education_resources_service.bean.entity.user.User;
import com.lss.education_resources_service.datasource.repository.UserRepository;
import com.lss.education_resources_service.framework.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.Set;


@Service
public class BaseService {

    private ApplicationContext mContext;

    @Autowired
    RedisCache redisCache;

    @Autowired
    UserRepository userRepository;

    public User getCurrentUser(){
        String token = (String) getHeader("token");
        return redisCache.getCacheObject(token);
    }

    public  Object  getHeader(String key){
        return getCurrentRequest().getHeader(key);
    }

    protected HttpServletRequest getCurrentRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest servletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
        return servletRequest;
    }

    public void refreshCache(Integer userId){
        User user = userRepository.findItemById(userId);
        String token = (String) getHeader("token");
        if (user!=null) {
            redisCache.setCacheObject(token,user);
        }
    }

    public void sendEvent(Object o){
        mContext.publishEvent(o);
    }
}
