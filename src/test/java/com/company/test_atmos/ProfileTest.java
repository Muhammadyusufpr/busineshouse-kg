package com.company.test_atmos;

import com.company.test_atmos.dto.request.AuthRequestDTO;
import com.company.test_atmos.dto.request.ProfileRequestDTO;
import com.company.test_atmos.dto.response.AuthResponseDTO;
import com.company.test_atmos.dto.response.ProfileResponseDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProfileTest {

    @Autowired
    private WebTestClient webTestClient;

    private String token;

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

    @Test
    void testGetAllProfiles() {
        // Send request to /api/v1/profile/profile-list endpoint with token in header
        EntityExchangeResult<List<ProfileResponseDTO>> response = webTestClient.get()
                .uri("/api/v1/profile/profile-list")
                .header("Authorization", String.format("Bearer %s", token))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(ProfileResponseDTO.class)
                .returnResult();
        System.out.println(response.getResponseBody());
    }


    @Test
    public void testEditProfile() {
        EntityExchangeResult<List<ProfileResponseDTO>> response = webTestClient.get()
                .uri("/api/v1/profile/profile-list")
                .header("Authorization", String.format("Bearer %s", token))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(ProfileResponseDTO.class)
                .returnResult();

        List<ProfileResponseDTO> profileList = response.getResponseBody();

        if (!profileList.isEmpty()) {
            // Send the request and verify the response
            ProfileResponseDTO profileResponseDTO = profileList.get(0);

            ProfileRequestDTO requestDTO = new ProfileRequestDTO();
            requestDTO.setName("Sarvar");
            requestDTO.setUsername("mukhtarov");

            EntityExchangeResult<ProfileResponseDTO> authorization = webTestClient.put()
                    .uri(String.format("/api/v1/profile/edit-profile/%s", profileResponseDTO.getId()))
                    .header("Authorization", String.format("Bearer %s", token))
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestDTO)
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectBody(ProfileResponseDTO.class)
                    .returnResult();

        }else {
            System.out.println("Profile is empty!");
        }

    }
}
