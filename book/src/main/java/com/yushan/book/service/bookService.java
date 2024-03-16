package com.yushan.book.service;

import java.util.List;

import com.yushan.book.pojo.Book;
import com.yushan.book.pojo.BorrowRecord;

public interface bookService {

	// 查詢所有可借書功能
	public List<Book> findAllBook();

	// 借書功能 可借:1 不可借:-1
	public int borrowBook(Integer userId, Integer inventoryId);

	// 還書功能 可還:1 不可還:-1
	public int returnBook(Integer userId, Integer inventoryId);

	// 查看書籍細項功能
	public Book findBookDetail(Integer inventoryId);

	// 查詢用戶已借書紀錄功能
	public List<BorrowRecord> findBorrowedBook(Integer userId);

}
