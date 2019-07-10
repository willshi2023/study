package org.springboot.demo.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/hello")
public class HelloController {
	@GetMapping("/hello")
	public String hello() {
		return "在地址栏输入http://localhost:8080/swagger-ui.html访问在线api";
	}
	
	@PostMapping(value = "/hello",produces = "application/json; charset=utf-8")
	public String pHello(@RequestBody JSONObject params) {
		return "你好，"+params.getString("name");
	}
}
