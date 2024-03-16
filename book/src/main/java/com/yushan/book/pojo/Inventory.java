package com.yushan.book.pojo;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "inventory")
public class Inventory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer inventoryId;

	private String isbn;

	private Timestamp storeTime;

	/*
	 * 在庫status = 0 / 出借中status = 1 / 整理中status = 2 / 遺失status = 3 / 損毀status = 4 /
	 * 廢棄status = 5
	 */
	private Integer status;

}
