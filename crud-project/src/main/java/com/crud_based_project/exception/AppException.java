package com.crud_based_project.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class AppException extends RuntimeException {

    private final int status;

    public AppException(String message, int status) {
        super(message);
        this.status = status;
    }

}
