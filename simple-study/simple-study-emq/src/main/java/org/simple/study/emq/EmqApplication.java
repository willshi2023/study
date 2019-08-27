package org.simple.study.emq;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.simple.study.emq.service.util.EMQUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class EmqApplication implements InitializingBean{

	public static void main(String[] args)   {
		SpringApplication.run(EmqApplication.class, args);
	}

	/**
	 * 实现InitializingBean接口初始化内容
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		String userName = "admin"; // 非必须
		String passWord = "public"; // 非必须
		String host = "tcp://192.168.99.100:1883";
		String topic = "pos_message_all";
		String clientId = "client11";
		MqttClient client=EMQUtil.connect(userName, passWord, host, clientId);
		EMQUtil.subscribe( topic, client);
	}
}
