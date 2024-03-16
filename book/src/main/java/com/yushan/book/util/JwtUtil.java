package com.yushan.book.util;

import java.util.Date;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JwtUtil {

	private static final String KEY = "danieljiang";

	// 生成token
	// 一小時後過期
	public static String getToken(Map<String, Object> claims) {
		return JWT.create().withClaim("claims", claims)
				.withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60)).sign(Algorithm.HMAC256(KEY));

	}

	// token轉換數據
	public static Map<String, Object> parseToken(String token) {
		return JWT.require(Algorithm.HMAC256(KEY)).build().verify(token).getClaim("claims").asMap();

	}

}
