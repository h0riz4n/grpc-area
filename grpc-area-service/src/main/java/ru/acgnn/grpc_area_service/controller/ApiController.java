package ru.acgnn.grpc_area_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.acgnn.grpc_area_service.service.UserService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final UserService userService;

    @GetMapping("/greeting")
    public ResponseEntity<String> getGreeting(
        @RequestParam("name") String name
    ) {
        return ResponseEntity.ok(userService.getGreeting(name));
    }
}
