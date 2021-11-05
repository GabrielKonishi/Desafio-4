package com.trilha.back.finacys.error;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
public class DefaultError implements Serializable {

	private static final long serialVersionUID = -7205564376968831935L;

	private String timestamp;
	private HttpStatus status;
	private String message;
	private Integer code;

	public DefaultError(String timestamp, HttpStatus status, Integer code, String message) {
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
