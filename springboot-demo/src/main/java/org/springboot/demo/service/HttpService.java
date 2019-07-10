package org.springboot.demo.service;

import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class HttpService {

	public static String postJson(String url,JSONObject body) {
		JSONObject head = new JSONObject();
		head.put("Content-Type", "application/json;charset=UTF-8");
		return postJson(url, body, head);
	}
	
	public static String postJson(String url,JSONObject body,JSONObject head) {
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			System.out.println(body.toJSONString());
			System.out.println(head.toJSONString());
			HttpPost httpPost = new HttpPost(url);
			for(Entry<String, Object> entry:head.entrySet()) {
				httpPost.addHeader(entry.getKey(), (String) entry.getValue());
			}
			StringEntity stringEntity = new StringEntity(body.toString());
			httpPost.setEntity(stringEntity);
			System.out.println("Executing request " + httpPost.getRequestLine());
			HttpEntity entity = httpclient.execute(httpPost).getEntity();
			String responseBody = EntityUtils.toString(entity);
			return responseBody;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static void main(String[] args) {
		JSONObject obj = new JSONObject();
		obj.put("name", "张三");
		String url = "http://localhost:8080/hello/hello";
		String result = postJson(url,obj);
		System.out.println(result);
	}
}
