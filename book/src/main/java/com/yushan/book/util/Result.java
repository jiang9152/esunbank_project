package com.yushan.book.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

	private Integer code; // 0:成功 1:失敗
	private String msg;
	private Object data;

	public static <E> Result<E> success(E data) {
		return new Result<>(0, "成功", data);
	}

	public static Result success() {
		return new Result<>(0, "成功", null);
	}

	public static Result error(String msg) {
		return new Result<>(1, msg, null);
	}

}
