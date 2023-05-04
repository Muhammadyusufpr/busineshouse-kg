package com.company.test_atmos.service.impl;

import com.company.test_atmos.dto.request.CityRequestDTO;
import com.company.test_atmos.dto.request.CityWeatherStatusDTO;
import com.company.test_atmos.dto.response.CityResponseDTO;
import com.company.test_atmos.entity.CityEntity;
import com.company.test_atmos.repository.CityRepository;
import com.company.test_atmos.service.CityService;
import com.company.test_atmos.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.data.relational.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final JwtUtil jwtUtil;

    @Override
    public Flux<CityResponseDTO> getAll() {
        return cityRepository
                .findAll()
                .map(CityResponseDTO::toDTO);
    }

    @Override
    public Mono<CityResponseDTO> update(String id, CityRequestDTO dto, String token) {
        boolean b = jwtUtil.hasEqual(token, "role", "ADMIN");
        if (!b) return Mono.empty();

        return cityRepository.findById(id)
                .switchIfEmpty(Mono.empty())
                .publishOn(Schedulers.boundedElastic())
                .map(entity -> {
                            entity.setName(dto.getName());
                            entity.setLatitude(dto.getLatitude());
                            entity.setLongitude(dto.getLongitude());
                            entity.setWeatherStatus(dto.getWeatherStatus().name());
                            cityRepository.save(entity).subscribe();
                            return CityResponseDTO.toDTO(entity);
                        }
                );
    }

    @Override
    public Mono<Void> updateWeatherStatus(String id, CityWeatherStatusDTO dto, String token) {
        boolean b = jwtUtil.hasEqual(token, "role", "ADMIN");
        if (!b) return Mono.empty();

        return r2dbcEntityTemplate.update(CityEntity.class)
                .matching(Query.query(Criteria.where("id").is(id)))
                .apply(Update.update("weather_status", dto.getStatus().name()))
                .then();
    }


    public Mono<CityEntity> get(String id) {
        return cityRepository.findById(id).switchIfEmpty(Mono.empty());
    }




}
