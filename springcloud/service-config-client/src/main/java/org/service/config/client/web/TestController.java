package org.service.config.client.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试没有加@RefreshScope注解就不会刷新配置
 * @author Administrator
 *
 */
@Controller
@RestController
@RequestMapping("/test")
@RefreshScope
public class TestController {
	@Value("${test}")
	String test;
	@RequestMapping(value = "/hi")
	public String hi(){
		return test;
	}
}
