package com.yushan.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.yushan.book.intercept.LoginInterceptors;

@Configuration
public class Configure implements WebMvcConfigurer {

	@Autowired
	private LoginInterceptors loginInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		// 登入頁和註冊頁不攔截
		registry.addInterceptor(loginInterceptor).excludePathPatterns("/user", "/user/new");
	}
}