package ru.acgnn.grpc_area_service.interceptor.grpc;

import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCall.Listener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import net.devh.boot.grpc.server.security.authentication.BearerAuthenticationReader;

@Slf4j
@Order(1)
@RequiredArgsConstructor
@GrpcGlobalServerInterceptor
public class SecurityGrpcInterceptor implements ServerInterceptor {

    private final BearerAuthenticationReader authReader;
    private final AuthenticationManager authManager;

    @Override
    public <ReqT, RespT> Listener<ReqT> interceptCall(
        ServerCall<ReqT, RespT> call, 
        Metadata headers,
        ServerCallHandler<ReqT, RespT> next
    ) {
        Authentication auth = authReader.readAuthentication(call, headers);
        if (auth != null) {
            SecurityContextHolder.getContext().setAuthentication(authManager.authenticate(auth));
        } else {
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        return next.startCall(call, headers);
    }
}
