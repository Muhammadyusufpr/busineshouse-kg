package com.company.test_atmos.service.impl;

import com.company.test_atmos.dto.request.AuthRegisDTO;
import com.company.test_atmos.dto.request.AuthRequestDTO;
import com.company.test_atmos.dto.response.AuthResponseDTO;
import com.company.test_atmos.entity.ProfileEntity;
import com.company.test_atmos.enums.ProfileRole;
import com.company.test_atmos.repository.ProfileRepository;
import com.company.test_atmos.service.AuthenticationService;
import com.company.test_atmos.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final ProfileRepository profileRepository;
    private final JwtUtil jwtUtil;


    @Override
    public Mono<AuthResponseDTO> authenticate(AuthRequestDTO dto) {
        return profileRepository.findByUsernameAndVisibleIsTrue(dto.getUsername())
                .mapNotNull(existingUser -> {
                    if (existingUser.getPassword().equals(dto.getPassword())) {
                        return new AuthResponseDTO(existingUser.getName(),
                                jwtUtil.generateToken(dto.getUsername(), ProfileRole.valueOf(existingUser.getRole())));
                    }
                    return null;
                });
    }


    @Override
    public Mono<?> register(AuthRegisDTO dto) {
        ProfileEntity entity = new ProfileEntity();

        entity.setName(dto.getName());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setRole("USER");
        entity.setCreatedDate(LocalDateTime.now());
        Mono<Object> usernameIsAlreadyTaken = profileRepository.findByUsernameAndVisibleIsTrue(dto.getUsername())
                .flatMap(existingUser -> Mono.error(new IllegalArgumentException("Username is already taken")))
                .switchIfEmpty(profileRepository.save(entity));
        entity.setId(UUID.randomUUID().toString());
        return usernameIsAlreadyTaken;
    }


}
