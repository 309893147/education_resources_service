package com.education.resources.controller.rest.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.education.resources.bean.entity.user.User;
import com.education.resources.bean.vo.UserLoginVo;
import com.education.resources.service.UserService;
import com.education.resources.util.HttpClientUtil;
import com.education.resources.util.rest.API;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;

@RequestMapping("/api")
@RestController
@Api(tags = "反馈相关接口")
public class WXUserAPI {
	@Autowired
	UserService userService;
	@ResponseBody
	@PostMapping("/wxLogin")
	public API wxLogin(String code) {
		// 微信get地址
		// https://api.weixin.qq.com/sns/
		// jscode2session?
		// appid=APPID&
		// secret=SECRET&
		// js_code=JSCODE&g
		// rant_type=authorization_code

		System.out.println("" + code);
		String url = "https://api.weixin.qq.com/sns/jscode2session";
		Map<String, String> param = new HashMap<String, String>();
		param.put("appid", "wxc49e51168e1768aa");
		param.put("secret", "cf3e414b70c5381de8db68306c1aa2f6");
		param.put("js_code", code);
		param.put("grant_type", "authorization_code");

		String wxResult = HttpClientUtil.doGet(url, param);
		JSONObject wxJsonResult = JSONObject.parseObject(wxResult);

		System.out.println(wxResult);
		// WXSessionModel model = JsonUtils.jsonToPojo(wxResult, WXSessionModel.class);
		// 作为session到
		return API.ok(wxJsonResult);

	}

	@PostMapping("/wxSaveUser")
	public API<UserLoginVo> wxSaveUser(User user) {
		return API.ok(userService.addWXUser(user));
	}

	@PostMapping("/updateUser")
	public API<User> updateUser(User user) {
		return API.ok(userService.updateUser(user));
	}

	@GetMapping("/getUser")
	public API<User> findById(Integer id){
		return API.ok(userService.getOne(id));
	}
}
