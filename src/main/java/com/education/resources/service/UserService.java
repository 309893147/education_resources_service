package com.education.resources.service;

import com.education.resources.bean.auth.Manager;
import com.education.resources.bean.entity.user.User;
import com.education.resources.bean.from.PageForm;
import com.education.resources.bean.vo.UserLoginVo;
import com.education.resources.datasource.repository.UserRepository;
import com.education.resources.framework.redis.RedisCache;
import com.education.resources.util.jpa.SpecificationUtil;
import com.education.resources.util.rest.APIError;
import com.github.wenhao.jpa.PredicateBuilder;
import com.github.wenhao.jpa.Specifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService extends BaseService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RedisCache redisCache;

    public User addWXUser(User user) {
        if (userRepository.count(Specifications.<User>and().eq("openId", user.getOpenId()).build()) > 0) {
            User myUser = userRepository.findOne(Specifications.<User>and().eq("openId", user.getOpenId()).build()).orElse(null);
            return myUser;
        }
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        user.setToken(token);
        userRepository.save(user);
        redisCache.setCacheObject("userToken_" + token, user);
        return user;
    }

    public User updateUser(User user) {
        User item = userRepository.findItemById(user.getId());
        item.setPresenceStatus(1);
        item.setAvatarUrl(user.getAvatarUrl());
        item.setNickName(user.getNickName());
        item.setAge(user.getAge());
        item.setGrade(user.getGrade());
        item.setGender(user.getGender());
        item.setMobile(user.getMobile());
        item.setEmail(user.getEmail());
        item.setId(user.getId());
        return userRepository.saveAndFlush(item);
}

    public Page<User> getUserList(User user, PageForm pageForm) {
        PredicateBuilder<User> spec = SpecificationUtil.filter(user);
        return userRepository.findAll(spec.build(), pageForm.pageRequest());
    }

    public User getOne(Integer id) {
        return userRepository.findItemById(id);
    }
}
