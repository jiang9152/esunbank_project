package com.yushan.book.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yushan.book.pojo.BorrowRecord;

import jakarta.transaction.Transactional;

@Repository
public interface RecordRepository extends JpaRepository<BorrowRecord, Integer> {

	// 5.查詢某用戶目前借出書籍的紀錄
	@Query(value = "call findBorrowedBook(:userId)", nativeQuery = true)
	public List<BorrowRecord> findBorrowedBook(@Param("userId") Integer userId);

	// 8.更新書籍狀態並新增紀錄
	@Modifying
	@Transactional
	@Query(value = "call updateBookStat(:status, :userId, :inventoryId)", nativeQuery = true)
	public void updateBookStat(@Param("status") Integer status, @Param("userId") Integer userId,
			@Param("inventoryId") Integer inventoryId);
}
