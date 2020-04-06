package com.education.resources.controller.rest.api;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.education.resources.annotation.AnonUrl;
import com.education.resources.bean.entity.ManagerApply;
import com.education.resources.bean.entity.user.User;
import com.education.resources.bean.from.ApplyManagerFrom;
import com.education.resources.service.UserService;
import com.education.resources.service.auth.AuthManagerService;
import com.education.resources.util.HttpClientUtil;
import com.education.resources.util.rest.API;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
//@Api(tags = "反馈相关接口")
public class WXUserAPI {
	@Autowired
	UserService userService;

	@Autowired
	AuthManagerService userManagerService;

	@AnonUrl
	@ResponseBody
	@PostMapping("/wxLogin")
	public API wxLogin(String code) {
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

	@AnonUrl
	@PostMapping("/wxSaveUser")
	public API<User> wxSaveUser(User user) {
		return API.ok(userService.addWXUser(user));
	}

	@AnonUrl
	@PostMapping("/updateUser")
	public API<User> updateUser(User user) {
		return API.ok(userService.updateUser(user));
	}

	@AnonUrl
	@GetMapping("/getUser")
	public API<User> findById(Integer id){
		return API.ok(userService.getOne(id));
	}

//	@ApiOperation("用户申请后台账号")
	@PostMapping(value = "/user/manager")
	public API<ManagerApply>  userApplyManager(@RequestBody ApplyManagerFrom applyManagerFrom){
		ManagerApply userManager = userManagerService.createUserManager(applyManagerFrom);
		if (userManager==null) {
			return API.e("请完善个人资料电话号码,后台账号将根据电话号码为用户名");
		}
		return API.ok(userManager);
	}

}
