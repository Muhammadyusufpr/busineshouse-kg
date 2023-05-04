package com.company.test_atmos.controller;

import com.company.test_atmos.dto.response.CityProfileDTO;
import com.company.test_atmos.dto.response.CityProfileResponseDTO;
import com.company.test_atmos.service.impl.CityProfileServiceImpl;
import com.company.test_atmos.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profile-city")
public class CityProfileController {
    private final CityProfileServiceImpl cityProfileService;
    private final JwtUtil jwtUtil;

    /**
     * USER
     */
    @PostMapping("/subscribe-to-city/{cityId}")
    public Mono<?> subscribeToCity(@PathVariable("cityId") String cityId,
                                   @RequestHeader("Authorization") String token) {
        String[] s = token.split(" ");

        boolean b = jwtUtil.hasEqual(s[1], "role", "USER");
        if (!b) return Mono.empty();

        String username = jwtUtil.getSubject(s[1]);

        return cityProfileService.subscribeToCity(cityId, username)
                .switchIfEmpty(Mono.empty());
    }

    @GetMapping("/get-subscriptions")
    public Flux<CityProfileResponseDTO> getSubscriptions(@RequestHeader("Authorization") String token) {
        String[] s = token.split(" ");
        return cityProfileService.getSubscriptions(s[1]).switchIfEmpty(Flux.empty());
    }

    /**
     * ADMIN
     */

    @GetMapping("/profile-details")
    public Flux<CityProfileDTO> profileDetails(@RequestHeader("Authorization") String token) {
        String[] s = token.split(" ");
        return cityProfileService.profileDetails(s[1]).switchIfEmpty(Flux.empty());
    }

}
