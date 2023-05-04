package com.company.test_atmos.controller;

import com.company.test_atmos.dto.request.CityRequestDTO;
import com.company.test_atmos.dto.request.CityWeatherStatusDTO;
import com.company.test_atmos.dto.response.CityResponseDTO;
import com.company.test_atmos.service.impl.CityServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/city")
public class CityController {
    private final CityServiceImpl cityService;


    @GetMapping("/cities-list")
    public Flux<CityResponseDTO> getAll() {
        return cityService.getAll();
    }

    /**
     * ADMIN
     */

    @PutMapping("/edit-city/{id}")
    public Mono<CityResponseDTO> editCity(@PathVariable("id") String id,
                                          @RequestBody CityRequestDTO dto,
                                          @RequestHeader("Authorization") String token) {
        String[] s = token.split(" ");
        return cityService.update(id, dto, s[1]);
    }


    @PutMapping("/update-city-weather/{id}")
    public Mono<Void> updateCityWeather(@PathVariable("id") String id,
                                        @RequestBody CityWeatherStatusDTO dto,
                                        @RequestHeader("Authorization") String token) {
        String[] s = token.split(" ");
        return cityService.updateWeatherStatus(id, dto, s[1]);
    }


}
