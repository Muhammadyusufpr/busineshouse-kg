package com.company.test_atmos.service;

import com.company.test_atmos.dto.response.CityProfileDTO;
import com.company.test_atmos.dto.response.CityProfileResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CityProfileService {
    Mono<Void> subscribeToCity(String cityId, String profileId);

    Flux<CityProfileResponseDTO> getSubscriptions(String token);

    Flux<CityProfileDTO> profileDetails(String token);
}
