package org.eureka.balance.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/test")
public class TestController {

	@Autowired
	RestTemplate restTemplate;

	/**
	 * 实例化RestTemplate
	 * 
	 * @return
	 */
	@LoadBalanced
	@Bean
	public RestTemplate rest() {
		return new RestTemplate();
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/get_user")
	public Map<String, Object> getUser(@RequestParam("id") Integer id) {
		Map<String, Object> data = new HashMap<>();
		/**
		 * 小伙伴发现没有，地址居然是http://service-provider 居然不是http://127.0.0.1:8082/
		 * 因为他向注册中心注册了服务，服务名称service-provider,我们访问service-provider即可
		 */
		data = restTemplate.getForObject("http://eureka-service-test/test/get_user?id=" + id, Map.class);
		return data;
	}
}
