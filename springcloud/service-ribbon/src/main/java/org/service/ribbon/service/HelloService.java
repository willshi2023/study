package org.service.ribbon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HelloService {
	@Autowired
    RestTemplate restTemplate;

	/**
	 * 直接根据名字即可做负载均衡，自动找到ip和端口号
	 * @param name
	 * @return
	 */
    public String hiService(String name) {
        return restTemplate.getForObject("http://SERVICE-HI/hi?name="+name,String.class);
    }
}
