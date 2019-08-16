package org.springboot.cookie.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CookieController {
	// 首先，想要获取Cookie信息，那么就得先有Cookie信息，这边我们自己从头开始，先弄个Cookie吧。

	@RequestMapping(value = "/setCookies", method = RequestMethod.GET)
	public String setCookies(HttpServletResponse response) {
		// HttpServerletRequest 装请求信息类
		// HttpServerletRespionse 装相应信息的类
		Cookie cookie = new Cookie("sessionId", "CookieTestInfo");
		response.addCookie(cookie);
		return "获得cookies信息成功";
	}

	// 非注解方式获取cookie中对应的key值

	@RequestMapping(value = "/getCookies", method = RequestMethod.GET)
	public String getCookies(HttpServletRequest request) {
		// HttpServletRequest 装请求信息类
		// HttpServletRespionse 装相应信息的类
		// Cookie cookie=new Cookie("sessionId","CookieTestInfo");
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("sessionId")) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
	
	//注解方式获取cookie中对应的key值
	@RequestMapping("/testCookieValue")
	public String testCookieValue(@CookieValue("sessionId") String sessionId ) {
	   //前提是已经创建了或者已经存在cookie了，那么下面这个就直接把对应的key值拿出来了。
	   System.out.println("testCookieValue,sessionId="+sessionId);
	    return "SUCCESS";
	}
}
