package org.dubbo.provider.service;

import org.dubbo.api.service.HelloService;

public class HelloServiceImpl implements HelloService{

	@Override
	public String sayHello(String name) {
		return "hello "+name;
	}

}
