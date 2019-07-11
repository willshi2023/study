package org.springboot.demo.web;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

/**
 * 用于测试http请求的controller
 * @author shijia
 *
 */
@RestController
@RequestMapping("/http")
public class HttpController implements InitializingBean{
	private Map<String,String> jobMap = new ConcurrentHashMap<>();
	
	@GetMapping("/get")
	public String get() {
		return "成功访问get请求";
	}
	
	@GetMapping("/get_job_info")
	public String getJobInfo(@RequestParam("jobName")String jobName) {
		if(jobMap.containsKey(jobName)) {
			return jobMap.get(jobName);
		}else {
			return "查询失败，没有这个任务";
		}
	}
	
	@PostMapping("/get_job_info")
	public String getJobInfoP(@RequestParam("jobName")String jobName) {
		if(jobMap.containsKey(jobName)) {
			return jobMap.get(jobName);
		}else {
			return "查询失败，没有这个任务";
		}
	}
	
	@PostMapping(value = "/hello",produces = "application/json; charset=utf-8")
	public String pHello(@RequestBody JSONObject params) {
		return "你好，"+params.getString("name");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		jobMap.put("annotiontask_20190711_test001", "success");
		jobMap.put("annotiontask_20190711_test002", "failure");
		jobMap.put("annotiontask_20190711_test003", "waitting");
	}
}
