package org.emq.service;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.emq.service.util.EMQUtil;

public class EMQPublish {
	
	/**
	 * 启动入口
	 * 
	 * @param args
	 * @throws MqttException
	 */
	public static void main(String[] args) throws MqttException {
		String userName="admin";
		String passWord="public";
		String host = "tcp://192.168.99.100:1883";
		String topic = "pos_message_all";
		String clientid = "server11";
		String payloadMessage1 = "推送第一条消息";
		String payloadMessage2 = "推送第二条消息";
		String payloadMessage3 = "推送第三条消息";
		MqttClient client = EMQUtil.connect(userName, passWord, host, clientid);
		EMQUtil.publicMessage(topic, payloadMessage1, client);
		EMQUtil.publicMessage(topic, payloadMessage2, client);
		EMQUtil.publicMessage(topic, payloadMessage3, client);
	}

}
