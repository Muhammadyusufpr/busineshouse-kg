package com.company.test_atmos.dto.response;

import com.company.test_atmos.entity.CityEntity;
import com.company.test_atmos.enums.WeatherStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CityResponseDTO {
    String id;
    String name;
    Double latitude;
    Double longitude;
    WeatherStatus weatherStatus;

    public static CityResponseDTO toDTO(CityEntity entity) {
        return CityResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .weatherStatus(WeatherStatus.valueOf(entity.getWeatherStatus()))
                .build();
    }
}
