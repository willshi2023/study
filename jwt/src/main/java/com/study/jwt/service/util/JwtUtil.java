package com.study.jwt.service.util;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtil {

	/**
	 * 过期时间,测试使用20分钟
	 */
	final static long EXPIRE_TIME = 1000 * 60 * 20;

	public static String setToken(String userName, String password) {
		long timestamp = System.currentTimeMillis() + EXPIRE_TIME;
		Date date = new Date(timestamp);
		Algorithm algorithm = Algorithm.HMAC256(password);
		return JWT.create().withClaim("userName", userName).withExpiresAt(date).sign(algorithm);
	}

	/**
	 * 校验token是否正确
	 *
	 * @param token  密钥
	 * @param secret 用户的密码
	 * @return 是否正确
	 */
	public static boolean verify(String token, String userName, String password) {
		try {
			DecodedJWT decode = JWT.decode(token);
			Date expires = decode.getExpiresAt();
			Date now = new Date();
			if (expires.after(now)) {
				throw new Exception("token已超时，请重新登录");
			}
			Algorithm algorithm = Algorithm.HMAC256(password);
			JWTVerifier verifier = JWT.require(algorithm).withClaim("userName", userName).build();
			verifier.verify(token);
			return true;
		} catch (Exception exception) {
			return false;
		}
	}

	/**
	 * 获得token中的信息无需secret解密也能获得
	 *
	 * @return token中包含的用户名
	 */
	public static String getUserName(String token) {
		DecodedJWT jwt = JWT.decode(token);
		return jwt.getClaim("userName").asString();
	}
}
