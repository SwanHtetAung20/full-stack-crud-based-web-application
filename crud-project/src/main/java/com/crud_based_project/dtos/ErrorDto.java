package com.crud_based_project.dtos;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ErrorDto {

    private String message;
    private HttpStatus statusCode;
}
