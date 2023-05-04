package com.company.test_atmos;

import com.company.test_atmos.dto.request.AuthRequestDTO;
import com.company.test_atmos.dto.response.*;
import com.company.test_atmos.service.impl.CityProfileServiceImpl;
import com.company.test_atmos.service.impl.CityServiceImpl;
import com.company.test_atmos.util.JwtUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProfileCityTest {

    @Autowired
    private WebTestClient webTestClient;

    public ProfileCityTest() {
        MockitoAnnotations.initMocks(this);
    }

    String token;
    String tokenForUser;

    @BeforeAll
    public void setup() {
        // Get authentication token from AuthController
        AuthRequestDTO authRequestDTO = new AuthRequestDTO();
        authRequestDTO.setUsername("admin");
        authRequestDTO.setPassword("123");

        WebTestClient.RequestHeadersSpec<?> requestHeadersSpec = webTestClient.post().uri("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON).body(Mono.just(authRequestDTO), AuthRequestDTO.class);
        token = requestHeadersSpec
                .exchange()
                .expectStatus().isOk()
                .expectBody(AuthResponseDTO.class)
                .returnResult()
                .getResponseBody()
                .getToken();
    }

    @BeforeAll
    public void setupToUser() {
        // Get authentication token from AuthController
        AuthRequestDTO authRequestDTO = new AuthRequestDTO();
        authRequestDTO.setUsername("user");
        authRequestDTO.setPassword("123");

        WebTestClient.RequestHeadersSpec<?> requestHeadersSpec = webTestClient.post().uri("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON).body(Mono.just(authRequestDTO), AuthRequestDTO.class);
        tokenForUser = requestHeadersSpec
                .exchange()
                .expectStatus().isOk()
                .expectBody(AuthResponseDTO.class)
                .returnResult()
                .getResponseBody()
                .getToken();
    }


    @Test
    public void testProfileDetails() {

        webTestClient.get().uri("/api/v1/profile-city/profile-details")
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CityProfileDTO.class);
    }

    @Test
    public void testGetSubscriptions() {
        EntityExchangeResult<List<CityProfileResponseDTO>> authorization = webTestClient.get().uri("/api/v1/profile-city/get-subscriptions")
                .header("Authorization", String.format("Bearer %s",token))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CityProfileResponseDTO.class)
                .returnResult();
        authorization.getResponseBody().forEach(System.out::println);
    }


    @Test
    public void testSubscriptions() {

        EntityExchangeResult<List<CityResponseDTO>> response = webTestClient.get()
                .uri("/api/v1/city/cities-list")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(CityResponseDTO.class)
                .returnResult();

        List<CityResponseDTO> cityResponseDTOList = response.getResponseBody();
        System.out.println(cityResponseDTOList);

        if (cityResponseDTOList.isEmpty()) {
            System.out.println("City list is empty!");
        } else {
            webTestClient.post().uri(String.format("/api/v1/profile-city/subscribe-to-city/%s",
                            cityResponseDTOList.get(0).getId()))
                    .header("Authorization", "Bearer " + tokenForUser)
                    .exchange()
                    .expectStatus().isOk();

        }
    }


}
