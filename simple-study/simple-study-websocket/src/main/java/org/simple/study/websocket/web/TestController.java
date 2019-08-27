package org.simple.study.websocket.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/test")
public class TestController {
	// 推送数据接口
	@ResponseBody
	@RequestMapping("/socket/push/{cid}")
	public Object pushToWeb(@PathVariable String cid, @RequestParam("message")String message) {
		JSONObject jsonObject = new JSONObject();
		try {
			WebSocketServer.sendInfo(message, cid);
			jsonObject.put("msg", "发送成功");
		} catch (Exception e) {
			jsonObject.put("msg", "发送失败");
			if(e instanceof NullPointerException) {
				jsonObject.put("error", "客户端已退出");
			}else {
				e.printStackTrace();
				jsonObject.put("error", e);
			}
		}
		return jsonObject;
	}
}
