package org.simple.study.jwt.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

	/**
	 * 获取需要认证的接口
	 * 
	 * @return
	 */
	@PostMapping("/get_private_data")
	public Map<String, Object> getPrivateData() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "success");
		map.put("msg", "操作成功");
		map.put("code", "00000");
		map.put("data", "this is private data");
		return map;
	}
}
