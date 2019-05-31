package org.emq.service;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.emq.service.util.EMQUtil;

public class EMQSubscribe {

	public static void main(String[] args) throws MqttException {
		String userName = "admin"; // 非必须
		String passWord = "public"; // 非必须
		String host = "tcp://192.168.99.100:1883";
		String topic = "pos_message_all";
		String clientId = "client11";
		// host为主机名，clientid即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
		// MQTT的连接设置
		MqttClient client = EMQUtil.connect(userName, passWord, host, clientId);
		EMQUtil.subscribe(topic, client);
	}

}
