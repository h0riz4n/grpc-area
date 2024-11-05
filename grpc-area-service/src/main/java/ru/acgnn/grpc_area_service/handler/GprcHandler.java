package ru.acgnn.grpc_area_service.handler;

import org.springframework.http.HttpStatus;

import io.grpc.Status;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;
import ru.acgnn.grpc_area_service.exception.ApiServiceException;

@GrpcAdvice
public class GprcHandler {

    @GrpcExceptionHandler(ApiServiceException.class)
    public Status handleInvalidArgument(ApiServiceException e) {
        return getStatus(e.getStatus()).withDescription(e.getMessage());
    }

    private Status getStatus(HttpStatus status) {
        return switch (status) {
            case NOT_FOUND -> Status.NOT_FOUND;
            case BAD_REQUEST -> Status.INVALID_ARGUMENT;
            case CONFLICT -> Status.ALREADY_EXISTS;
            case FORBIDDEN -> Status.PERMISSION_DENIED;
            case UNAUTHORIZED -> Status.UNAUTHENTICATED;
            default -> Status.INTERNAL;
        };
    }
}
