package com.yushan.book.exceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.yushan.book.util.Result;

@RestControllerAdvice
public class ExceptionHandle {

	@ExceptionHandler(Exception.class)
	public Result handleException(Exception e) {

		if (e instanceof BindException) {
			// 參數校驗異常
			BindException ex = (BindException) e;

			List<String> messageList = ex.getBindingResult().getFieldErrors().stream()
					.map(FieldError::getDefaultMessage).collect(Collectors.toList());
			String message = String.valueOf(messageList);

			return Result.error(message);

		} else {
			// 其他異常
			e.printStackTrace();
			return Result.error(StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "發生錯誤");
		}
	}

}
