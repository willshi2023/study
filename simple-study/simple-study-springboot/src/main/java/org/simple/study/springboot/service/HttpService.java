package org.simple.study.springboot.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class HttpService {
	/**
	 * 发送get请求
	 * @param url
	 * @return
	 */
	public static String get(String url) {
		JSONObject head = new JSONObject();
		JSONObject param = new JSONObject();
		return get(url, param, head);
	};
	
	
	/**
	 * 发送get请求，json格式的参数
	 * @param url
	 * @param param
	 * @return
	 */
	public static String get(String url,JSONObject param) {
		JSONObject head = new JSONObject();
		return get(url, param, head );
	};
	
	/**
	 * 发送get请求，json格式的参数以及json格式的信息头
	 * @param url
	 * @param param
	 * @param head
	 * @return
	 */
	public static String get(String url, JSONObject param, JSONObject head) {
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			for(Entry<String, Object> entry:param.entrySet()) {
				params.add(new BasicNameValuePair(entry.getKey(), (String) entry.getValue()));
			}
			String paramsStr = EntityUtils.toString(new UrlEncodedFormEntity(params, "UTF-8"));
			url = url + "?" + paramsStr;
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			for (Entry<String, Object> entry : head.entrySet()) {
				httpGet.addHeader(entry.getKey(), (String) entry.getValue());
			}
			CloseableHttpResponse response = httpClient.execute(httpGet);
			HttpEntity entry = response.getEntity();
			String result = EntityUtils.toString(entry);
			HttpClientUtils.closeQuietly(response);
			HttpClientUtils.closeQuietly(httpClient);
			return result;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 发送post请求，json格式的参数
	 * @param url
	 * @param param
	 * @return
	 */
	public static String post(String url,JSONObject param) {
		JSONObject head=new JSONObject();
		return post(url, param, head);
	}
	/**
	 * 发送post请求，json格式的参数以及json格式的信息头
	 * @param url
	 * @param param
	 * @param head
	 * @return
	 */
	public static String post(String url,JSONObject param, JSONObject head) {
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			for(Entry<String, Object> entry:param.entrySet()) {
				params.add(new BasicNameValuePair(entry.getKey(), (String) entry.getValue()));
			}
			UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(params, "UTF-8");
			httpPost.setEntity(urlEncodedFormEntity);
			for (Entry<String, Object> entry : head.entrySet()) {
				httpPost.addHeader(entry.getKey(), (String) entry.getValue());
			}
			HttpEntity entity = httpclient.execute(httpPost).getEntity();
			String responseBody = EntityUtils.toString(entity);
			return responseBody;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	};
	
	/**
	 * 发送postJson请求，json格式的参数
	 * 
	 * @param url
	 * @param param
	 * @return
	 */
	public static String postJson(String url, JSONObject param) {
		JSONObject head = new JSONObject();
		head.put("Content-Type", "application/json;charset=UTF-8");
		return postJson(url, param, head);
	}

	/**
	 * 发送postJson请求，json格式的参数以及json格式的信息头
	 * 
	 * @param url
	 * @param param
	 * @param head
	 * @return
	 */
	public static String postJson(String url, JSONObject param, JSONObject head) {
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			for (Entry<String, Object> entry : head.entrySet()) {
				httpPost.addHeader(entry.getKey(), (String) entry.getValue());
			}
			StringEntity stringEntity = new StringEntity(param.toString(),"utf-8");
			httpPost.setEntity(stringEntity);
			HttpEntity entity = httpclient.execute(httpPost).getEntity();
			String responseBody = EntityUtils.toString(entity);
			return responseBody;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static void main(String[] args) {
		JSONObject param = new JSONObject();
		param.put("jobName", "annotiontask_20190711_test001");
		String url = "http://localhost:8080/http/get_job_info";
		String result = post(url, param);
		System.out.println("result:"+result);
		String result2 = get(url, param);
		System.out.println(result2);
		url = "http://localhost:8080/http/hello";
		param.put("name", "张三");
		String result3 = postJson(url, param);
		System.out.println(result3);
		url = "http://localhost:8080/http/get";
		String result4 = get(url);
		System.out.println(result4);
	}
}
