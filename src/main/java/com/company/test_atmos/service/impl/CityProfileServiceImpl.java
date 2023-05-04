package com.company.test_atmos.service.impl;

import com.company.test_atmos.dto.response.CityProfileDTO;
import com.company.test_atmos.dto.response.CityProfileResponseDTO;
import com.company.test_atmos.entity.CityEntity;
import com.company.test_atmos.entity.ProfileCityEntity;
import com.company.test_atmos.entity.ProfileEntity;
import com.company.test_atmos.exception.ItemNotFoundException;
import com.company.test_atmos.repository.CityProfileRepository;
import com.company.test_atmos.service.CityProfileService;
import com.company.test_atmos.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CityProfileServiceImpl implements CityProfileService {
    private final CityServiceImpl cityService;
    private final ProfileServiceImpl profileService;
    private final CityProfileRepository cityProfileRepository;
    private final JwtUtil jwtUtil;


    @Override
    public Mono<Void> subscribeToCity(String cityId, String username) {

        cityService.get(cityId)
                .switchIfEmpty(Mono.error(new ItemNotFoundException("Item not found!")))
                .block();

        ProfileEntity profile = profileService.getByUsername(username)
                .switchIfEmpty(Mono.error(new ItemNotFoundException("Item not found!")))
                .block();

        ProfileCityEntity entity = new ProfileCityEntity();

        entity.setProfileId(profile.getId());
        entity.setCityId(cityId);
        entity.setCreatedDate(LocalDateTime.now());

        return cityProfileRepository.save(entity).then();
    }


    @Override
    public Flux<CityProfileResponseDTO> getSubscriptions(String token) {
        boolean hasUserRole = jwtUtil.hasEqual(token, "role", "USER");
        if (!hasUserRole) {
            return Flux.empty();
        }

        return cityProfileRepository.findAll()
                .flatMap(profileCity -> {
                    Mono<CityEntity> cityMono = cityService.get(profileCity.getCityId());
                    Mono<ProfileEntity> profileMono = profileService.get(profileCity.getProfileId());

                    return Mono.zip(cityMono, profileMono)
                            .map(tuple -> CityProfileResponseDTO.toDTO(tuple.getT2(), tuple.getT1(), profileCity));
                });
    }


    @Override
    public Flux<CityProfileDTO> profileDetails(String token) {
        boolean b = jwtUtil.hasEqual(token, "role", "ADMIN");
        if (!b) return Flux.empty();
        return cityProfileRepository.findAllSub();
    }

}
