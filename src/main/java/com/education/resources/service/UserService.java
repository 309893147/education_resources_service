package com.education.resources.service;

import com.education.resources.bean.auth.Manager;
import com.education.resources.bean.entity.Resource;
import com.education.resources.bean.entity.ResourceUserDownload;
import com.education.resources.bean.entity.user.User;
import com.education.resources.bean.from.PageForm;
import com.education.resources.bean.vo.UserLoginVo;
import com.education.resources.datasource.repository.ResourceUserDownloadRepository;
import com.education.resources.datasource.repository.UserRepository;
import com.education.resources.datasource.repository.resource.ResourceRepository;
import com.education.resources.framework.redis.RedisCache;
import com.education.resources.util.jpa.SpecificationUtil;
import com.education.resources.util.rest.APIError;
import com.github.javafaker.Faker;
import com.github.wenhao.jpa.PredicateBuilder;
import com.github.wenhao.jpa.Specifications;
import org.apache.commons.collections.CollectionUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.sql.DriverManager;
import java.util.*;

@Service
public class UserService extends BaseService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ResourceRepository resourceRepository;

    @Autowired
    ResourceUserDownloadRepository resourceUserDownloadRepository;

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


    public void makeFaker() {
        for (int i = 0; i < 99; i++) {
            Faker faker = new Faker(new Locale("zh-CN"));
            User user = new User();
            user.setNickName(faker.name().fullName());
            user.setGender(faker.number().numberBetween(0, 1));
            user.setGrade(faker.number().numberBetween(1, 7));
            user.setAge(faker.number().numberBetween(7, 65) + "");
            User item = userRepository.save(user);
            Integer userId = item.getId();
        }
    }

    public void makeFaker2() {
        List<Resource> resourceList = resourceRepository.findAll();
        List<User> userList = userRepository.findAll();
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<Integer> list2 = new ArrayList<>();
        for (Resource resource : resourceList) {
            list.add(resource.getId());
        }

        Random rand = new Random();
        for (User user : userList) {
            list2.clear();
            CollectionUtils.addAll(list2, new Object[list.size()]);
            Collections.copy(list2, list);
            for (int i = 0; i < 7; i++) {
                Integer integer = list2.get(rand.nextInt(list2.size()));
                list2.remove(integer);
                ResourceUserDownload resourceUserDownload = new ResourceUserDownload();
                resourceUserDownload.setUserId(user.getId());
                resourceUserDownload.setResourceId(integer);
                Faker faker = new Faker(new Locale("zh-CN"));
                resourceUserDownload.setScore(faker.number().numberBetween(0, 100));
                resourceUserDownloadRepository.save(resourceUserDownload);
            }
        }
    }
}
