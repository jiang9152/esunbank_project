package com.yushan.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yushan.book.pojo.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

	// 6.查詢某本書的庫存狀態
	@Query(value = "call findBookStat(:inventoryId)", nativeQuery = true)
	public Inventory findBookStat(@Param("inventoryId") Integer inventoryId);

}
