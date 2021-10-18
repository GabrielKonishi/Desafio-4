package com.trilha.back.finacys.exception;

import java.time.Instant;

import javax.servlet.ServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trilha.back.finacys.error.DefaultError;

@ControllerAdvice
public class ValidateExceptionHandler {

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseEntity<DefaultError> validateExceptionHandler(ValidateException ve, ServletRequest request){
		DefaultError error = new DefaultError(
				Instant.now(),
				ve.getStatus(),
				ve.getStatus().value(),
				ve.getMessage());
		
		return new ResponseEntity<DefaultError>(error, ve.getStatus());
		
	}
}
