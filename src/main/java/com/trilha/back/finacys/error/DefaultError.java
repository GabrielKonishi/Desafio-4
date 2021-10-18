package com.trilha.back.finacys.error;

import java.io.Serializable;
import java.time.Instant;

import org.springframework.http.HttpStatus;

public class DefaultError implements Serializable {

	private static final long serialVersionUID = -7205564376968831935L;

	private Instant timestamp;
	private HttpStatus status;
	private String message;
	private Integer code;

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public DefaultError(Instant timestamp, HttpStatus status, Integer code, String message) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.message = message;
		this.code = code;
	}

	public DefaultError() {
		super();
	}

}
