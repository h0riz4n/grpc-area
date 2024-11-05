package ru.acgnn.grpc_area_service.exception;


import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ApiServiceException extends RuntimeException {

    private final HttpStatus status;

    public ApiServiceException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
