package org.springboot.transaction.jms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class CustomerService {
	private static final Logger LOG = LoggerFactory.getLogger(CustomerService.class);
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@JmsListener(destination = "customer:msg1:new")
	public void handle(@RequestParam String msg) {
		LOG.info("Get msg1:{}",msg);
		String reply = "Reply1-"+msg;
		jmsTemplate.convertAndSend("customer:msg:reply",reply);
		if(msg.contains("error")) {
			simulateError();
		}
	}
	
	private void simulateError() {
		throw new RuntimeException("aaa ");
	}
	
}
