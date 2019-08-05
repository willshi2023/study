package org.interview.web;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.interview.entity.User;
import org.interview.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {
	@Autowired
	private UserMapper userMapper;
	@GetMapping("/inser-user")
	@ResponseBody
	public String inserUser() {
		List<User> list = new ArrayList<User>();
		for(int i=0;i<50000;i++) {
			User user = new User();
			String string = UUID.randomUUID().toString();
			user.setUsername(string.substring(0, 15));
			user.setPassword(string.substring(string.length()-10,string.length()-1));
			list.add(user);
		}
		userMapper.insertMultiUser(list);
		return "success";
	}
}
