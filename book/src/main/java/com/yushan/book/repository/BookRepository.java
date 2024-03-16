package com.yushan.book.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yushan.book.pojo.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

	// 4.查詢所有可借書
	@Query(value = "call findAllBook()", nativeQuery = true)
	public List<Book> findAllBook();

	// 7.查詢某本書籍的細節資訊
	@Query(value = "call findBookDetail(:inventoryId)", nativeQuery = true)
	public Book findBookDetail(@Param("inventoryId") Integer inventoryId);

}
