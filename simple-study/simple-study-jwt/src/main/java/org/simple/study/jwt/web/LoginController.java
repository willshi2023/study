package org.simple.study.jwt.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.simple.study.jwt.service.UserService;
import org.simple.study.jwt.service.util.JwtUtil;

@RestController
@RequestMapping("/login")
public class LoginController {
	/**
	 * 登录操作 登录成功可以拿到token，以后在请求头里面加一个Authorization：$token就行了
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	@PostMapping("/login")
	public Map<String, Object> login(@RequestParam("username") String username,
			@RequestParam("password") String password) {
		boolean checkLogin = UserService.checkLogin(username, password);
		Map<String, Object> map = new HashMap<String, Object>();
		if (checkLogin) {
			map.put("status", "success");
			map.put("msg", "登录成功");
			map.put("code", "00000");
			String token = JwtUtil.setToken(username, password);
			map.put("token", token);
		} else {
			map.put("status", "failure");
			map.put("msg", "登录失败");
			map.put("code", "00001");
		}
		return map;
	}

}
