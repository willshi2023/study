package org.springboot.cookie.web;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {
	/**
	 * session相当于是为每个浏览器单独设置的map，里面可以存放任意的东西，和其他人互不可见
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/setSession", method = RequestMethod.GET)
	public String setSession(HttpServletRequest request,HttpServletResponse response) {
		String sessionUser = UUID.randomUUID().toString();
		request.getSession().setAttribute("user", sessionUser);
		return "设置session信息成功:"+sessionUser;
	}
	
	/**
	 * session是服务器为每个浏览器单独提供的，如果切换了ip或者浏览器并且没有设置user，那么session就获取不到user，打印的就是null
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getSession", method = RequestMethod.GET)
	public String getSession(HttpServletRequest request,HttpServletResponse response) {
		String sessionUser = (String) request.getSession().getAttribute("user");
		return "获得session信息成功:"+sessionUser;
	}
	
}
