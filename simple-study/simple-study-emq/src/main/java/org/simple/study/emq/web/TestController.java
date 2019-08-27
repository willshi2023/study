package org.simple.study.emq.web;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.simple.study.emq.service.util.EMQUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/test")
public class TestController {
	private static MqttClient client;
	
	@PostMapping("/test_publish_message")
	public Object testPublishMessage(@RequestParam("topic")String topic,@RequestParam("payload_message")String payloadMessage) {
		JSONObject jsonObject = new JSONObject();
		try {
			String userName="admin";
			String passWord="public";
			String host = "tcp://192.168.99.100:1883";
			String clientid = "server11";
			if(client==null) {
				client = EMQUtil.connect(userName, passWord, host, clientid);
			}
			EMQUtil.publicMessage(topic, payloadMessage, client);
			jsonObject.put("msg", "操作成功");
		} catch (MqttException e) {
			e.printStackTrace();
			jsonObject.put("msg", "操作失败");
			jsonObject.put("error", e);
		}
		return jsonObject;
	}
}
