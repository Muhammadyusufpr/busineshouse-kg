package com.company.test_atmos;

import com.company.test_atmos.dto.request.AuthRegisDTO;
import com.company.test_atmos.dto.request.AuthRequestDTO;
import liquibase.pro.packaged.T;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestAtmosApplicationTests {

    @Autowired
    private WebTestClient webTestClient;
    @Test
    void registration() {
        AuthRegisDTO request = new AuthRegisDTO();
        request.setName("MuhammadYusuf");
        request.setUsername("gapirov");
        request.setPassword("qwerty");

        webTestClient.post().uri("/api/v1/auth/register")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void login() {
        AuthRequestDTO request = new AuthRequestDTO();
        request.setPassword("qwerty");
        request.setUsername("gapirov");

        webTestClient.post().uri("/api/v1/auth/login")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("MuhammadYusuf")
                .jsonPath("$.token").isNotEmpty();
    }

}
