package ru.acgnn.grpc_area_service.service;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import ru.acgnn.grpc.client.UserServiceGrpc.UserServiceBlockingStub;
import ru.acgnn.grpc.client.Request;
import ru.acgnn.grpc.client.UserServiceGrpc.UserServiceBlockingStub;

@Slf4j
@Service
public class UserService {

    @GrpcClient("user-client")
    private UserServiceBlockingStub userGrpcClient;

    public String getGreeting(String name) {
        Request message = Request.newBuilder()
            .setName(name)
            .build();
        String response = userGrpcClient.sayHello(message).getMessage();
        log.debug("gRPC response - {}", response);
        return response;
    }
}
