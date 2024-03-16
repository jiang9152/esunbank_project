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
@Table(name = "borrowing_record")
public class BorrowRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer recordId;

	private Integer userId;

	private Integer inventoryId;

	private Timestamp borrowingTime;

	private Timestamp returnTime;

}
