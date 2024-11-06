package ru.acgnn.grpc_area_service.handler;

import org.springframework.http.HttpStatus;

import io.grpc.Status;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;
import ru.acgnn.grpc_area_service.exception.ApiServiceException;

@Slf4j
@GrpcAdvice
public class GprcHandler {

    @GrpcExceptionHandler(ApiServiceException.class)
    public Status handleInvalidArgument(ApiServiceException e) {
        log.debug("ApiServiceException: {}", e.getMessage());
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
