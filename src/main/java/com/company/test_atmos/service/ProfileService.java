package com.company.test_atmos.service;

import com.company.test_atmos.dto.request.ProfileRequestDTO;
import com.company.test_atmos.dto.response.ProfileResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProfileService {
    Flux<ProfileResponseDTO> getAll(String token);
    Mono<ProfileResponseDTO> update(ProfileRequestDTO dto, String id, String token);



}
