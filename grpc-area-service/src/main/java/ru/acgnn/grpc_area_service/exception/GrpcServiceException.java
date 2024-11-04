package ru.acgnn.grpc_area_service.exception;


import io.grpc.Status;
import lombok.Getter;

@Getter
public class GrpcServiceException extends RuntimeException {

    private final Status status;

    public GrpcServiceException(String message, Status status) {
        super(message);
        this.status = status;
    }
}
