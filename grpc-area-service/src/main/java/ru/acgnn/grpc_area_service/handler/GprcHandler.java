package ru.acgnn.grpc_area_service.handler;

import io.grpc.Status;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;
import ru.acgnn.grpc_area_service.exception.GrpcServiceException;

@GrpcAdvice
public class GprcHandler {

    @GrpcExceptionHandler(GrpcServiceException.class)
    public Status handleInvalidArgument(GrpcServiceException e) {
        return e.getStatus().withDescription(e.getMessage());
    }
}
