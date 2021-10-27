package com.trilha.back.finacys.exception;

import org.springframework.http.HttpStatus;


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

    public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	

}
