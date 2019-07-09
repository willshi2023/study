package org.emq.service.util;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class EMQUtil {

	/**
	 * 发布消息
	 * 
	 * @param topic
	 * @param payloadMessage
	 * @param client
	 * @throws MqttException
	 * @throws MqttPersistenceException
	 */
	public static void publicMessage(String topic, String payloadMessage, MqttClient client)
			throws MqttException, MqttPersistenceException {
		MqttMessage message = new MqttMessage();
		message.setQos(1);
		message.setRetained(false);
		message.setPayload(payloadMessage.getBytes());
		client.publish(topic, message);
	}

	/**
	 * 设置连接方法
	 * 
	 * @param userName
	 * @param passWord
	 * @param host
	 * @param clientid
	 * @return
	 * @throws MqttException
	 * @throws MqttSecurityException
	 */
	public static MqttClient connect(String userName, String passWord, String host, String clientid)
			throws MqttException, MqttSecurityException {
		MqttClient connect = connect(userName, passWord, host, clientid, new MqttCallback() {

			@Override
			public void messageArrived(String topic, MqttMessage message) throws Exception {
				// subscribe后得到的消息会执行到这里面
				System.out.println("接收消息主题 : " + topic);
				System.out.println("接收消息Qos : " + message.getQos());
				System.out.println("接收消息内容 : " + new String(message.getPayload()));
			}

			@Override
			public void deliveryComplete(IMqttDeliveryToken token) {
				System.out.println("deliveryComplete---------" + token.isComplete());
			}

			@Override
			public void connectionLost(Throwable cause) {
				// 连接丢失后，一般在这里面进行重连
				System.out.println("连接断开，可以做重连");
			}
		});
		return connect;
	}

	/**
	 * 自定义监听请求数据的方法
	 * 
	 * @param userName
	 * @param passWord
	 * @param host
	 * @param clientid
	 * @param mqttCallback
	 * @return
	 * @throws MqttException
	 * @throws MqttSecurityException
	 */
	public static MqttClient connect(String userName, String passWord, String host, String clientid,
			MqttCallback mqttCallback) throws MqttException, MqttSecurityException {
		MqttClient client = new MqttClient(host, clientid, new MemoryPersistence());
		MqttConnectOptions options = new MqttConnectOptions();
		options.setCleanSession(false);
		options.setUserName(userName);
		options.setPassword(passWord.toCharArray());
		// 设置超时时间
		options.setConnectionTimeout(10);
		// 设置会话心跳时间
		options.setKeepAliveInterval(20);
		client.setCallback(mqttCallback);
		client.connect(options);
		return client;
	}

	/**
	 * 订阅消息
	 * 
	 * @param userName
	 * @param passWord
	 * @param host
	 * @param topic
	 * @param clientId
	 * @throws MqttException
	 * @throws MqttSecurityException
	 */
	public static void subscribe(String topic, MqttClient client) throws MqttException, MqttSecurityException {
		// 订阅消息
		int[] Qos = { 1 };
		String[] topic1 = { topic };
		client.subscribe(topic1, Qos);
	}

}
