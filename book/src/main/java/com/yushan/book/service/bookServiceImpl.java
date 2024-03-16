package com.yushan.book.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yushan.book.pojo.Book;
import com.yushan.book.pojo.BorrowRecord;
import com.yushan.book.pojo.Inventory;
import com.yushan.book.repository.BookRepository;
import com.yushan.book.repository.InventoryRepository;
import com.yushan.book.repository.RecordRepository;

@Service
public class bookServiceImpl implements bookService {

	@Autowired
	private BookRepository bookRepo;
	@Autowired
	private InventoryRepository invRepo;
	@Autowired
	private RecordRepository recRepo;

	@Override
	public List<Book> findAllBook() {

		return bookRepo.findAllBook();
	}

	@Override
	public int borrowBook(Integer userId, Integer inventoryId) {

		// 確認狀態為可借閱
		Inventory inv = invRepo.findBookStat(inventoryId);

		// 更新狀態
		if (inv.getStatus() == 0) {
			recRepo.updateBookStat(1, userId, inventoryId);
			return 1;
		}
		return -1;
	}

	@Override
	public int returnBook(Integer userId, Integer inventoryId) {

		// 確認狀態為借出
		Inventory inv = invRepo.findBookStat(inventoryId);

		// 更新狀態
		if (inv.getStatus() == 1) {
			recRepo.updateBookStat(0, userId, inventoryId);
			return 1;
		}
		return -1;
	}

	@Override
	public Book findBookDetail(Integer inventoryId) {

		return bookRepo.findBookDetail(inventoryId);
	}

	@Override
	public List<BorrowRecord> findBorrowedBook(Integer userId) {

		return recRepo.findBorrowedBook(userId);
	}

}
