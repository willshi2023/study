package org.websocket.web;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@ServerEndpoint("/websocket/{sid}")
@Component
public class WebSocketServer {
	private static Logger log = LoggerFactory.getLogger(WebSocketServer.class);
	private static ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<>();
	
	@OnOpen
	public void onOpen(Session session,@PathParam("sid") String sid){
		log.info("程序进入onOpen方法");
		sessionMap.put(sid, session);
	}
	@OnClose
	public void onClose(@PathParam("sid") String sid){
		log.info("程序进入onClose方法");
		sessionMap.remove(sid);
	}
	@OnMessage
	public void onMessage(String message, Session session){
		log.info("程序进入onMessage方法");
	}
	
	@OnError
	public void onError(Session session, Throwable error){
		log.info("程序进入onError方法");
	}
	
	/**
	 * 发送消息
	 * @param message
	 * @param cid
	 * @throws IOException
	 */
	public static void sendInfo(String message, String cid) throws IOException {
		Session session = sessionMap.get(cid);
		session.getBasicRemote().sendText(message);
	}
}
