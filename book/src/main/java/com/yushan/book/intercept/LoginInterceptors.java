package com.yushan.book.intercept;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptors implements HandlerInterceptor {

	@Autowired
	private StringRedisTemplate template;

	// 登入驗證
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = request.getHeader("Authorization");
		try {
			// 從redis取出token
			ValueOperations<String, String> operations = template.opsForValue();
			String redisToken = operations.get(token);

			// token已失效
			if (redisToken == null) {
				throw new RuntimeException();
			}

			// 成功
			return true;
		} catch (Exception e) {
			// 失敗
			response.setStatus(401);
			return false;
		}
	}

}
