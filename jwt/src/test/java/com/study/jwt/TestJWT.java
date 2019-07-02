package com.study.jwt;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import com.study.jwt.service.util.JwtUtil;

public class TestJWT {
	public static void main(String[] args) throws UnsupportedEncodingException {
		String userName="zhangsan";
		String password="123456";
		String token = JwtUtil.setToken(userName, password);
		System.out.println(token);
		String[] split = token.split("\\.");
		for(int i=0;i<split.length;i++) {
			String str = split[i];
			System.out.println("第"+(i+1)+"部分");
			byte[] decode = Base64.getDecoder().decode(str.getBytes("utf-8"));
			System.out.println(new String(decode,"utf-8"));
		}
	}
}
