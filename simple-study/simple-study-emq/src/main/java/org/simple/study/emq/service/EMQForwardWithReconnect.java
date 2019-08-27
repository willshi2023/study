package org.simple.study.emq.service;

import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class EMQForwardWithReconnect {
	private LinkedBlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<>();

	public LinkedBlockingQueue<String> getLinkedBlockingQueue() {
		return linkedBlockingQueue;
	}

	public void setLinkedBlockingQueue(LinkedBlockingQueue<String> linkedBlockingQueue) {
		this.linkedBlockingQueue = linkedBlockingQueue;
	}

	String userName; // 非必须
	String password; // 非必须
	String host;
	String subscribeTopic;
	String publishTopic;
	String clientId;
	MqttConnectOptions option;
	MqttClient client;
	int outTime = 10;
	int keepTime = 20;

	public EMQForwardWithReconnect(String userName, String password, String host, String subscribeTopic,
			String publishTopic, String clientId) {
		this.userName = userName;
		this.password = password;
		this.host = host;
		this.subscribeTopic = subscribeTopic;
		this.publishTopic = publishTopic;
		this.clientId = clientId;
	}

	public static void main(String[] args) {
		String userName = "admin"; // 非必须
		String password = "public"; // 非必须
		String host = "ws://192.168.99.100:8083";
		String subscribeTopic = "/World";
		String publishTopic = "/World2";
		String clientId = UUID.randomUUID().toString();
		EMQForwardWithReconnect testEmqReConnect = new EMQForwardWithReconnect(userName, password, host, subscribeTopic,
				publishTopic,clientId);
		testEmqReConnect.forwardWithReconnect();
	}

	/**
	 * 自动进行重连和订阅的方法
	 * 
	 * @param eMQSubscribeWithReconnect
	 */
	public void forwardWithReconnect() {
		while (true) {
			boolean connect = connect();
			if (connect) {
				System.out.println("客户端" + this.clientId + ":" + "连接成功");
				break;
			} else {
				System.out.println("客户端" + this.clientId + ":" + "连接失败，10秒后进行重连");
				try {
					Thread.sleep(10_000);
				} catch (InterruptedException e) {
					System.err.println("客户端" + this.clientId + ":" + e.toString());
				}
			}
		}
		try {
			this.client.subscribe(this.subscribeTopic, 1);
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					while(true) {
						try {
							String msg = linkedBlockingQueue.take();
							MqttMessage message = new MqttMessage();
							message.setQos(1);
							message.setRetained(false);
							message.setPayload(msg.getBytes());
							client.publish(publishTopic, message);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} catch (MqttPersistenceException e) {
							e.printStackTrace();
						} catch (MqttException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
		} catch (MqttException e) {
			System.err.println("客户端" + this.clientId + ":" + e.toString());
		}
	}

	private synchronized boolean connect() {
		try {
			if (null == client) {
				// host为主机名，clientid即连接MQTT的客户端ID，一般以唯一标识符表示，
				// MemoryPersistence设置clientid的保存形式，默认为以内存保存
				client = new MqttClient(host, clientId, new MemoryPersistence());
				// 设置回调
				client.setCallback(new PushCallBack());
			}
			// 获取连接配置
			getOption();
			client.connect(option);
			System.out.println("客户端" + clientId + ":" + "[MQTT] connect to Mqtt Server success...");
			return client.isConnected();
		} catch (Exception e) {
			System.err.println("客户端" + clientId + ":" + e.toString());
			return false;
		}
	}

	private void getOption() {
		// MQTT连接设置
		option = new MqttConnectOptions();
		// 设置是否清空session,false表示服务器会保留客户端的连接记录，true表示每次连接到服务器都以新的身份连接
		option.setCleanSession(true);
		// 设置连接的用户名
		option.setUserName(userName);
		// 设置连接的密码
		option.setPassword(password.toCharArray());
		// 设置超时时间 单位为秒
		option.setConnectionTimeout(outTime);
		// 设置会话心跳时间 单位为秒 服务器会每隔(1.5*keepTime)秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
		option.setKeepAliveInterval(keepTime);
		option.setAutomaticReconnect(true);
		// setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息
//            option.setWill(topic, "close".getBytes(), 2, true);
	}

	private class PushCallBack implements MqttCallback {

		private void reConnect() throws Exception {
			if (null != client) {
				client.connect(option);
			}
		}

		@Override
		public void connectionLost(Throwable cause) {
			// 连接丢失后，一般在这里面进行重连
			System.out.println("客户端" + clientId + ":" + "[MQTT] 连接断开，10S之后尝试重连...");
			while (true) {
				try {
					Thread.sleep(10_000);
					reConnect();
					client.subscribe(subscribeTopic, 1);
					System.out.println("客户端" + clientId + ":" + "重连成功");
					break;
				} catch (Exception e) {
					System.err.println("客户端" + clientId + ":" + e.toString());
					if (!e.toString().contains("无法连接至服务器 (32103)") && !e.toString().contains("sleep interrupted")) {
						e.printStackTrace();
					}
				}
			}
		}

		@Override
		public void messageArrived(String topic, MqttMessage message) throws Exception {
			System.out.println("客户端" + clientId + ":" + "接收消息主题 : " + topic);
			System.out.println("客户端" + clientId + ":" + "接收消息Qos : " + message.getQos());
			String msg = new String(message.getPayload());
			System.out.println("客户端" + clientId + ":" + "接收消息内容 : " + msg);
			linkedBlockingQueue.add(msg);
		}

		@Override
		public void deliveryComplete(IMqttDeliveryToken token) {
			System.out.println("客户端" + clientId + ":" + "deliveryComplete---------" + token.isComplete());
		}

	}
}
