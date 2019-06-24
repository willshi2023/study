package org.springboot.transaction.web;

import org.springboot.transaction.entity.User;
import org.springboot.transaction.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserMapper userMapper;
	
	/**
	 * 增加一个事务，当抛出异常的时候可以回滚当前操作
	 * @throws Exception 
	 */
	@Transactional
	@GetMapping("/test-transaction")
	public void testTransaction() throws RuntimeException {
		User user = new User();
		user.setName("张三杉");
		userMapper.insertUser(user);
		throw new RuntimeException("手动抛出一个自定义异常");
	}
}
