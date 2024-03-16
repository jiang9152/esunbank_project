package com.yushan.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yushan.book.pojo.User;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	// 1.電話號碼查詢用戶
	@Query(value = "call findByNumber(:number)", nativeQuery = true)
	public User findByNumber(@Param("number") String phone);

	// 2.註冊用戶
	@Modifying
	@Transactional
	@Query(value = "call register(:number, :password, :username)", nativeQuery = true)
	public void register(@Param("number") String phone, @Param("password") String password,
			@Param("username") String username);

	// 3.用戶登入更新時間
	@Modifying
	@Transactional
	@Query(value = "call login(:number)", nativeQuery = true)
	public void login(@Param("number") String phone);

}
