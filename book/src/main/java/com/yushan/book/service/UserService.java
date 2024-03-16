package com.yushan.book.service;

import com.yushan.book.pojo.User;

public interface UserService {

	// 註冊功能
	public void register(String phone, String password, String username);

	// 查詢功能
	public User findByNumber(String phone);

	// 登入功能
	public void login(String phone);

}
