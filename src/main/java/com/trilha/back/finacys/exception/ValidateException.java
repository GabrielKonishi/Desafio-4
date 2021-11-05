package com.trilha.back.finacys.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ValidateException extends RuntimeException {

	private static final long serialVersionUID = -1663613332685576789L;
	private HttpStatus status;
	private String msg;
	private Integer code;
	private String detail;

	public ValidateException(String message) {

		super(message);
	}

	public ValidateException(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}
	
	public ValidateException(Integer code, String msg, String detail) {
		super();
		this.code = code;
		this.msg = msg;
		this.detail = detail;
	}

    public ValidateException() {

    }

}
