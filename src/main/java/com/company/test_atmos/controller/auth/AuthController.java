package com.company.test_atmos.controller.auth;

import com.company.test_atmos.dto.request.AuthRegisDTO;
import com.company.test_atmos.dto.request.AuthRequestDTO;
import com.company.test_atmos.dto.response.AuthResponseDTO;
import com.company.test_atmos.service.impl.AuthenticationServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthenticationServiceImpl authenticationService;


    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponseDTO>> login(@RequestBody AuthRequestDTO dto) {
        return authenticationService.authenticate(dto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<String>> register(@RequestBody AuthRegisDTO dto) {
        return authenticationService.register(dto)
                .map(registeredUser -> ResponseEntity.ok("Registration successful"));
    }

}
