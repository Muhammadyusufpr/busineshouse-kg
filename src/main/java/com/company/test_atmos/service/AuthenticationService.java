package com.company.test_atmos.service;

import com.company.test_atmos.dto.request.AuthRegisDTO;
import com.company.test_atmos.dto.request.AuthRequestDTO;
import com.company.test_atmos.dto.response.AuthResponseDTO;
import reactor.core.publisher.Mono;

public interface AuthenticationService {
    Mono<AuthResponseDTO> authenticate(AuthRequestDTO dto);

    Mono<?> register(AuthRegisDTO dto);
}
