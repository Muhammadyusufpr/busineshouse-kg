package com.company.test_atmos;

import com.company.test_atmos.controller.CityController;
import com.company.test_atmos.dto.request.AuthRequestDTO;
import com.company.test_atmos.dto.request.CityRequestDTO;
import com.company.test_atmos.dto.request.CityWeatherStatusDTO;
import com.company.test_atmos.dto.response.AuthResponseDTO;
import com.company.test_atmos.dto.response.CityResponseDTO;
import com.company.test_atmos.enums.WeatherStatus;
import com.company.test_atmos.repository.CityRepository;
import com.company.test_atmos.service.impl.CityServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CityTest {
    @Mock
    private CityServiceImpl cityService;
    @Autowired
    @Mock
    private WebTestClient webTestClient;

    @InjectMocks
    private CityController cityController;





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

        WebTestClient.RequestHeadersSpec<?> requestHeadersSpec = webTestClient
                .post()
                .uri("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(authRequestDTO), AuthRequestDTO.class);
        tokenForUser = requestHeadersSpec
                .exchange()
                .expectStatus().isOk()
                .expectBody(AuthResponseDTO.class)
                .returnResult()
                .getResponseBody()
                .getToken();
    }

    @Test
    public void testEditCity() {

        String id = "1a38qwdd3c-7ccf-416a-8c0c-f9a0ae61e9102";
        CityRequestDTO requestDTO = new CityRequestDTO();
        requestDTO.setName("New York");
        requestDTO.setLatitude(40.7128);
        requestDTO.setLongitude(74.0060);
        requestDTO.setWeatherStatus(WeatherStatus.COLD);


        CityResponseDTO responseDTO = new CityResponseDTO();
        responseDTO.setId("1a38qwdd3c-7ccf-416a-8c0c-f9a0ae61e9102");
        responseDTO.setName("New York");
        responseDTO.setLatitude(40.7128);
        responseDTO.setLongitude(74.0060);
        responseDTO.setWeatherStatus(WeatherStatus.COLD);

        when(cityService.update(id, requestDTO,
                token))
                .thenReturn(Mono.just(responseDTO));

        webTestClient.put().uri("/api/v1/city/edit-city/1")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CityResponseDTO.class);
    }


    @Test
    public void testEditWeatherCity() {

        String id = "1a38qwdd3c-7ccf-416a-8c0c-f9a0ae61e9102";
        CityWeatherStatusDTO requestDTO = new CityWeatherStatusDTO();
        requestDTO.setStatus(WeatherStatus.COLD);

        when(cityService.updateWeatherStatus(id, requestDTO,
                token)
        );

        webTestClient.put().uri("/api/v1/city/update-city-weather/" + id)
                .header("Authorization",String.format("Bearer %s",token))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestDTO)
                .exchange()
                .expectStatus().isOk();
    }


    @Test
    public void testGetAllCities() {
        CityResponseDTO city1 = CityResponseDTO.builder()
                .id("1")
                .name("City 1")
                .latitude(12.34)
                .longitude(56.78)
                .weatherStatus(WeatherStatus.SUNNY)
                .build();
        CityResponseDTO city2 = CityResponseDTO.builder()
                .id("2")
                .name("City 2")
                .latitude(23.45)
                .longitude(67.89)
                .weatherStatus(WeatherStatus.CLOUDY)
                .build();

        when(cityService.getAll()).thenReturn(Flux.just(city1, city2));

        Flux<CityResponseDTO> result = cityController.getAll();


        StepVerifier.create(result)
                .expectNext(city1)
                .expectNext(city2)
                .expectComplete()
                .verify();
    }


}
