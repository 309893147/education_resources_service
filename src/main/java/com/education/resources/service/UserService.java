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
public class UserService extends BaseService{

	@Autowired
	UserRepository userRepository;

	@Autowired
	RedisCache redisCache;

	public UserLoginVo<User> addWXUser(User user) {
//		if(user.getId() == null && userRepository.count(Specifications.<User>and().eq("openId",user.getOpenId()).build())>0){
//			System.out.println("获取redis缓存");
//			User myUser = userRepository.findOne(Specifications.<User>and().eq("openId", user.getOpenId()).build()).orElse(null);
//			UserLoginVo<User> cacheObject = redisCache.getCacheObject("userToken_" + user.getOpenId());
//			UserLoginVo<User> vo = UserLoginVo.<User>builder().token(cacheObject.getToken()).data(myUser).build();
//			return vo;
//		}
		User item = userRepository.save(user);
		String token = UUID.randomUUID().toString().replaceAll("-", "");

		//加入缓存
		UserLoginVo<User> vo = UserLoginVo.<User>builder().token(token).data(item).build();
		redisCache.setCacheObject("userToken_"+token,item);
		return vo;
	}

	public User updateUser(User user){
		user.setPresenceStatus(1);
		return userRepository.saveAndFlush(user);
	}

	public Page<User> getUserList(User user, PageForm pageForm){
		PredicateBuilder<User> spec = SpecificationUtil.filter(user);
		return userRepository.findAll(spec.build(),pageForm.pageRequest());
	}

	public User getOne(Integer id){
		return userRepository.findItemById(id);
	}
}
