package ru.acgnn.grpc_area_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class GrpcAreaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GrpcAreaServiceApplication.class, args);
	}

}

