package com.to.wms.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionDto> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            HttpServletRequest httpServletRequest
    ) {
        BindingResult bindingResult = ex.getBindingResult();
        ValidationExceptionDto dto = new ValidationExceptionDto(
                Timestamp.valueOf(LocalDateTime.now()),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                bindingResult.getFieldErrors().get(0).getDefaultMessage(),
                httpServletRequest.getRequestURI()
        );
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

}
