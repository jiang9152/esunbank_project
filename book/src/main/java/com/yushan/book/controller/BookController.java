package com.yushan.book.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yushan.book.pojo.Book;
import com.yushan.book.pojo.BorrowRecord;
import com.yushan.book.service.bookService;
import com.yushan.book.util.JwtUtil;
import com.yushan.book.util.Result;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/book")
@Validated
public class BookController {

	@Autowired
	private bookService bookService;

	// 查看所有可借的書
	@GetMapping
	public Result<List<Book>> getAllBook() {

		List<Book> list = bookService.findAllBook();

		return Result.success(list);

	}

	// 查看已借書的紀錄
	@GetMapping("/mine")
	public Result<List<BorrowRecord>> getBorrowedBook(@RequestHeader(name = "Authorization") String token) {

		// 解析JWT token
		Map<String, Object> map = JwtUtil.parseToken(token);
		Integer userId = (Integer) map.get("id");

		List<BorrowRecord> list = bookService.findBorrowedBook(userId);

		return Result.success(list);

	}

	// 查看書的細項
	// 假設 inventoryId 最多到10000
	@GetMapping("/{inventoryId}")
	public Result<Book> findBookDetail(
			@Max(10000) @Min(1) @NotNull @PathVariable(value = "inventoryId") Integer inventoryId) {

		Book book = bookService.findBookDetail(inventoryId);

		return Result.success(book);

	}

	// 借書
	// 假設 inventoryId 最多到10000
	@PostMapping
	public Result borrowBook(@Max(10000) @Min(1) @NotNull @RequestParam(value = "inventoryId") Integer inventoryId,
			@RequestHeader(name = "Authorization") String token) {

		// 解析JWT token
		Map<String, Object> map = JwtUtil.parseToken(token);
		Integer userId = (Integer) map.get("id");

		// 借閱
		int i = bookService.borrowBook(userId, inventoryId);

		// 借閱成功
		if (i == 1) {
			return Result.success();
		}

		// 借閱失敗
		return Result.error("書籍狀態確認中，請稍後再試");

	}

	// 還書
	// 假設 inventoryId 最多到10000
	@PostMapping("/mine")
	public Result returnBook(@Max(10000) @Min(1) @NotNull @RequestParam(value = "inventoryId") Integer inventoryId,
			@RequestHeader(name = "Authorization") String token) {

		// 解析JWT token
		Map<String, Object> map = JwtUtil.parseToken(token);
		Integer userId = (Integer) map.get("id");

		// 歸還
		int i = bookService.returnBook(userId, inventoryId);

		// 歸還成功
		if (i == 1) {
			return Result.success();
		}

		// 歸還失敗
		return Result.error("書籍狀態確認中，請稍後再試");

	}
}
