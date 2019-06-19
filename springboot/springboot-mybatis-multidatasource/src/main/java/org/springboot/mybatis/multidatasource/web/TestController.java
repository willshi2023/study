package org.springboot.mybatis.multidatasource.web;

import org.springboot.mybatis.multidatasource.mapper.test1.User1Mapper;
import org.springboot.mybatis.multidatasource.mapper.test2.User2Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
	@Autowired
	private User1Mapper userMapper1;
	
	@Autowired
	private User2Mapper userMapper2;
	
	@GetMapping("/test_datasource1")
	public Object testDatasource1(@RequestParam("id") Integer id) {
		return userMapper1.getUsernameById(id);
	}
	
	@GetMapping("/test_datasource2")
	public Object testDatasource2(@RequestParam("id") Integer id) {
		return userMapper2.getUsernameById(id);
	}
}
