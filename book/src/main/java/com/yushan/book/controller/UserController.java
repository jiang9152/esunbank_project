package com.yushan.book.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yushan.book.pojo.User;
import com.yushan.book.service.UserService;
import com.yushan.book.util.JwtUtil;
import com.yushan.book.util.Result;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

	@Autowired
	private StringRedisTemplate template;

	@Autowired
	private UserService userService;

	// 註冊
	@PostMapping("/new")
	public Result<String> register(
			@NotEmpty(message = "電話不得為空") @Pattern(regexp = "^\\d{10}$", message = "電話為10位數字") @RequestParam(value = "userphone") String phone,
			@NotEmpty(message = "密碼不得為空") @Pattern(regexp = "^[a-zA-Z0-9]{5,10}$", message = "密碼為5到10位英文字母大小寫或數字") @RequestParam(value = "password") String password,
			@NotEmpty(message = "姓名不得為空") @Pattern(regexp = "^[a-zA-Z0-9]{5,30}$", message = "姓名為5到30位英文字母大小寫或數字") @RequestParam(value = "username") String username) {

		// 查詢是否註冊過
		User user = userService.findByNumber(phone);

		// 未註冊
		if (user == null) {

			// 註冊
			userService.register(phone, password, username);

			return Result.success("註冊成功，請登入");

		}

		return Result.error("您已註冊，請登入");

	}

	// 登入
	@PostMapping
	public Result<String> login(
			@NotEmpty(message = "電話不得為空") @Pattern(regexp = "^\\d{10}$", message = "電話為10位數字") @RequestParam(value = "userphone") String phone,
			@NotEmpty(message = "密碼不得為空") @Pattern(regexp = "^[a-zA-Z0-9]{5,10}$", message = "密碼為5到10位英文字母大小寫或數字") @RequestParam(value = "password") String password) {

		// 查詢是否註冊過
		User user = userService.findByNumber(phone);

		// 未註冊
		if (user == null) {

			return Result.error("您未註冊，請先註冊後再登入");

		} else {

			boolean check = BCrypt.checkpw(password, user.getPassword());

			if (check) {
				// 登入成功
				// 記錄登入時間
				userService.login(phone);

				// JWT token製作
				Map<String, Object> claims = new HashMap<>();
				claims.put("id", user.getUserId());
				claims.put("username", user.getUsername());
				String token = JwtUtil.getToken(claims);

				// token存入redis
				// 為在登出時直接用token查找，redis存入的key和value都設成token
				ValueOperations<String, String> operations = template.opsForValue();
				operations.set(token, token, 1, TimeUnit.HOURS);

				return Result.success(token);
			} else {
				// 登入失敗
				return Result.error("密碼錯誤，請重試");
			}

		}

	}

	// 登出
	@GetMapping
	public Result<String> logout(@RequestHeader(name = "Authorization") String token) {

		// 刪除redis中舊的token
		ValueOperations<String, String> operations = template.opsForValue();
		operations.getOperations().delete(token);

		return Result.success("登出成功");
	}
}
