package org.springboot.transaction.jms.service;

import javax.jms.ConnectionFactory;

import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.PlatformTransactionManager;

@EnableJms
@Configuration
public class JmsConfig {
	@Bean
	public PlatformTransactionManager transactionManager(ConnectionFactory cf) {
		return new JmsTransactionManager(cf);
	}
	
	@Bean
	public JmsTemplate jmsTemplate(ConnectionFactory cf) {
		JmsTemplate jmsTemplate = new JmsTemplate();
		jmsTemplate.setConnectionFactory(cf);
		return jmsTemplate;
	}
	
	@Bean
	public JmsListenerContainerFactory<?> msgFactory(ConnectionFactory connectionFactory,
			DefaultJmsListenerContainerFactoryConfigurer configurer,
			PlatformTransactionManager transactionManager
			){
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		configurer.configure(factory, connectionFactory);
		factory.setReceiveTimeout(10000L);
		factory.setCacheLevelName("CACHE_CONNECTION");
		factory.setTransactionManager(transactionManager);
		return factory;
	}
}
