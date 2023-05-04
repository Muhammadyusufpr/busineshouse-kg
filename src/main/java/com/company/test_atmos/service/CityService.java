package com.company.test_atmos.service;

import com.company.test_atmos.dto.request.CityRequestDTO;
import com.company.test_atmos.dto.request.CityWeatherStatusDTO;
import com.company.test_atmos.dto.response.CityResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CityService {
    Flux<CityResponseDTO> getAll();
    Mono<CityResponseDTO> update(String id, CityRequestDTO dto, String token);
    Mono<Void> updateWeatherStatus(String id, CityWeatherStatusDTO dto, String token);

}
