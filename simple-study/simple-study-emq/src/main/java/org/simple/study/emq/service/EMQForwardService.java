package org.simple.study.emq.service;

import java.util.concurrent.LinkedBlockingQueue;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.simple.study.emq.service.util.EMQUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

/**
 * EMQ转发的服务
 * 		转发规则，假设现在有app，服务器，终端多种类型的设备，每个设备都监听唯一的topic
 * 		这个唯一的topic好比是每个设备唯一的名字，要找那个就将topic设置成谁的监听topic，这样emq就会自动将消息转发给那个设备
 *        	，其他的设备协商都发布消息给服务器的topic，分布式环境下监听的topic则是（以购买任务为例）：
 * 		app： /buy/app/$userId
 * 		server： /buy/server/$serverNodeName
 * 		device： /buy/device/$deviceId
 * 	...
 * 		当各种类型的设备都发送消息给服务器做一个处理了之后，服务器转发给对应的目的设备，目的设备的获取是通过msg
 * 		比如 {"from":"app","to":"device","app_id":123,"device_id":342,"info":{"price":100,"goods_name":"面包"}}
 * 		当发给服务器了之后，服务器可以知道是谁发的，也可以知道发给谁，后面可以带一个信息显示是哪些商品和对应的价格，或者其他业务强相关的信息
 * @author shijia
 *
 */
@Component
public class EMQForwardService implements InitializingBean{
	private static LinkedBlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<>();
	private static Logger log = LoggerFactory.getLogger(EMQForwardService.class);

	public static LinkedBlockingQueue<String> getLinkedBlockingQueue() {
		return linkedBlockingQueue;
	}

	public static void setLinkedBlockingQueue(LinkedBlockingQueue<String> linkedBlockingQueue) {
		EMQForwardService.linkedBlockingQueue = linkedBlockingQueue;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		String userName="admin";
		String passWord="public";
		String host = "ws://192.168.99.100:8083";
		String topic1 = "/task/app/test";
		String topic2 = "/task/device/test";
		String topic3 = "/task/server/test";
		String clientid = "server11";
		MqttClient mqttClient = EMQUtil.connect(userName, passWord, host, clientid, new ForwordMqttCallBack());
		EMQUtil.subscribe(topic3, mqttClient);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					try {
						String msg = EMQForwardService.getLinkedBlockingQueue().take();
						// {"from":"app","to":"device","app_id":123,"device_id":342,"info":{"price":100,"goods_name":"面包"}}
						JSONObject jsonObject = JSONObject.parseObject(msg);
						String toString = (String) jsonObject.get("to");
						if(toString.equals("app")) {
							EMQUtil.publicMessage(topic1, msg, mqttClient);
						}else if(toString.equals("device")) {
							EMQUtil.publicMessage(topic2, msg, mqttClient);
						}
					} catch (InterruptedException | MqttException e) {
						e.printStackTrace();
						log.error("error:",e);
					}
				}
			}
		}).start();
	}
	
	class ForwordMqttCallBack implements MqttCallback{

		@Override
		public void connectionLost(Throwable cause) {
			log.info("连接丢失");
		}

		@Override
		public void messageArrived(String topic, MqttMessage message) throws Exception {
			String msg = new String(message.getPayload(),"utf-8");
			log.info("待转发的消息是："+msg);
			// 逻辑处理，自定义一些逻辑，比如安全校验，业务逻辑等等
			EMQForwardService.getLinkedBlockingQueue().add(msg);
			int size = EMQForwardService.getLinkedBlockingQueue().size();
			log.info("当前队列的长度是："+size);
		}

		@Override
		public void deliveryComplete(IMqttDeliveryToken token) {
			log.info("消息完成");
		}
		
	}
}
