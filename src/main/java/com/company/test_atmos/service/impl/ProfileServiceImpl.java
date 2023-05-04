package com.company.test_atmos.service.impl;

import com.company.test_atmos.dto.request.ProfileRequestDTO;
import com.company.test_atmos.dto.response.ProfileResponseDTO;
import com.company.test_atmos.entity.CityEntity;
import com.company.test_atmos.entity.ProfileEntity;
import com.company.test_atmos.exception.BadRequestException;
import com.company.test_atmos.repository.ProfileRepository;
import com.company.test_atmos.service.ProfileService;
import com.company.test_atmos.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private final JwtUtil jwtUtil;

    @Override
    public Flux<ProfileResponseDTO> getAll(String token) {
        boolean b = jwtUtil.hasEqual(token, "role", "ADMIN");
        if (!b) return Flux.empty();
        return profileRepository
                .findAll()
                .map(ProfileResponseDTO::toDTO)
                .switchIfEmpty(Flux.empty());
    }

    @Override
    public Mono<ProfileResponseDTO> update(ProfileRequestDTO dto, String id, String token) {
        boolean b = jwtUtil.hasEqual(token, "role", "ADMIN");
        if (!b) return Mono.empty();

        return profileRepository.findById(id)
                .publishOn(Schedulers.boundedElastic())
                .mapNotNull(entity -> {
                            ProfileEntity profile = profileRepository
                                    .findByUsernameAndVisibleIsTrue(dto.getUsername()).block();

                            if (profile != null && !profile.getId().equals(id)) {
                                Mono.error(() -> {
                                    throw new BadRequestException(" bad request");
                                }).block();
                            }

                            entity.setName(dto.getName());
                            entity.setUsername(dto.getUsername());
                            profileRepository.save(entity).subscribe();
                            return ProfileResponseDTO.toDTO(entity);
                        }
                );
    }


    public Mono<ProfileEntity> get(String id) {
        return profileRepository.findById(id).switchIfEmpty(Mono.empty());
    }

    public Mono<ProfileEntity> getByUsername(String username) {
        return profileRepository.findByUsernameAndVisibleIsTrue(username).switchIfEmpty(Mono.empty());
    }


}
