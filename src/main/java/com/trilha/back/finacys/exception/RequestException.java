package com.trilha.back.finacys.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class RequestException {

    private HttpStatus status;
    private String message;
    private Integer code;
    private String timestamp;
    private String field;
}
