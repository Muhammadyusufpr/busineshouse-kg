package com.company.test_atmos.controller;

import com.company.test_atmos.dto.request.ProfileRequestDTO;
import com.company.test_atmos.dto.response.ProfileResponseDTO;
import com.company.test_atmos.service.impl.ProfileServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profile")
public class ProfileController {
    private final ProfileServiceImpl profileService;

    /**
     * ADMIN
     */

    @GetMapping("/profile-list")
    public Flux<ProfileResponseDTO> getAll(@RequestHeader("Authorization") String token) {
        String[] s = token.split(" ");
        System.out.println(token);
        return profileService.getAll(s[1]);
    }


    @PutMapping("edit-profile/{id}")
    public Mono<ProfileResponseDTO> editProfile(@PathVariable("id") String id, @RequestBody ProfileRequestDTO dto,
                               @RequestHeader("Authorization") String token) {
        String[] s = token.split(" ");
        return profileService.update(dto, id, s[1]);
    }
}
