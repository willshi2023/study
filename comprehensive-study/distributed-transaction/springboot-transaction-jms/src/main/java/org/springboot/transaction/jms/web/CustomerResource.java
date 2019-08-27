package org.springboot.transaction.jms.web;

import org.springboot.transaction.jms.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer")
public class CustomerResource {
	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/message1/listen")
	public void create(@RequestParam String msg) {
		jmsTemplate.convertAndSend("customer:msg1:new", msg);
	}
	
	@PostMapping("/message1/direct")
	public void handle(@RequestParam String msg) {
		customerService.handle(msg);
	}
	
	@PostMapping("/message2/listen")
	public void create2(@RequestParam String msg) {
		jmsTemplate.convertAndSend("customer:msg2:new", msg);
	}
	
	@PostMapping("/message2/direct")
	public void handle2(@RequestParam String msg) {
		customerService.handleInCode(msg);
	}
	
	@GetMapping("/message")
	public String read() {
		jmsTemplate.setReceiveTimeout(2000);
		Object obj = jmsTemplate.receiveAndConvert("customer:msg:reply");
		return String.valueOf(obj);
	}
}
