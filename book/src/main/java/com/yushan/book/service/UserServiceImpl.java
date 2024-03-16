package com.yushan.book.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.yushan.book.pojo.User;
import com.yushan.book.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;

	@Override
	public void register(String phone, String password, String username) {

		// 加密
		String encodePwd = BCrypt.hashpw(password, BCrypt.gensalt());

		repository.register(phone, encodePwd, username);

	}

	@Override
	public User findByNumber(String phone) {
		return repository.findByNumber(phone);
	}

	@Override
	public void login(String phone) {
		repository.login(phone);
	}

}
