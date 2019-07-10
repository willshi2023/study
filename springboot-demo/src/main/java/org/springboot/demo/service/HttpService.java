package org.springboot.demo.service;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class HttpService {

	public static String postJson(JSONObject json, String url) {
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			System.out.println(json.toString());

			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");

			// 解决中文乱码问题
			StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");
			stringEntity.setContentEncoding("UTF-8");

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
		String result = postJson(obj, "http://localhost:8080/hello/hello");
		System.out.println(result);
	}
}
