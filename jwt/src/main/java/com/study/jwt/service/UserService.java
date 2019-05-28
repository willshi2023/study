package com.study.jwt.service;

import java.util.HashMap;
import java.util.Map;

public class UserService {
	private static Map<String, String> map = new HashMap<>();

	/**
	 * 根据用户名获取密码
	 * 
	 * @param userName
	 * @return
	 */
	public static String getPasswordByUserName(String userName) {
		if (map.isEmpty()) {
			initMap();
		}
		return map.get(userName);
	}

	/**
	 * 检查是否登录成功
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	public static boolean checkLogin(String userName, String password) {
		if (map.isEmpty()) {
			initMap();
		}
		return map.containsKey(userName) && map.get(userName).equals(password);
	}

	/**
	 * 初始化用户数据
	 */
	private static void initMap() {
		map.put("admin", "admin");
		map.put("user", "user");
	}

}
