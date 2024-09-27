package com.crud_based_project.exception;

import com.crud_based_project.dtos.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler {


    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorDto> exceptionHandler(AppException appException) {
        return new ResponseEntity<>(
                ErrorDto.builder()
                        .message(appException.getMessage())
                        .statusCode(appException.getStatus())
                        .build(),
                HttpStatus.EXPECTATION_FAILED
        );
    }
}
