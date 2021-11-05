package com.trilha.back.finacys.exception;

import com.trilha.back.finacys.error.DefaultError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ValidateException.class)
    public ResponseEntity<DefaultError> validateExceptionHandler(ValidateException ve) {
        DefaultError error = new DefaultError(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")),
                ve.getStatus(),
                ve.getStatus().value(),
                ve.getMessage());

        return new ResponseEntity<DefaultError>(error, ve.getStatus());

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RequestException> requestException(MethodArgumentNotValidException invalidException) {
        List<FieldError> fieldError = invalidException.getBindingResult().getFieldErrors();
        String fields = fieldError.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String fieldsMessage = fieldError.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

        return new ResponseEntity<RequestException>(
                RequestException.builder()
                        .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")))
                        .status(HttpStatus.BAD_REQUEST)
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(fieldsMessage)
                        .field(fields)
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<DefaultError> arithmeticExceptionHandler(ValidateException ve) {
        DefaultError error = new DefaultError(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")),
                ve.getStatus(),
                ve.getStatus().value(),
                ve.getMessage());

        return new ResponseEntity<DefaultError>(error, ve.getStatus());

    }
}
