package org.springboot.demo.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {
	@GetMapping("/hello")
	public String hello() {
		return "在地址栏输入http://localhost:8080/swagger-ui.html访问在线api";
	}
}
