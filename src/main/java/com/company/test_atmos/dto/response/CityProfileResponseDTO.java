package com.company.test_atmos.dto.response;

import com.company.test_atmos.entity.CityEntity;
import com.company.test_atmos.entity.ProfileCityEntity;
import com.company.test_atmos.entity.ProfileEntity;
import com.company.test_atmos.enums.WeatherStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CityProfileResponseDTO {
    String cityId;
    String profileId;
    CityResponseDTO city;
    ProfileResponseDTO profile;

    public static CityProfileResponseDTO toDTO(ProfileEntity profile,
                                               CityEntity city,
                                               ProfileCityEntity profileCity) {
        return CityProfileResponseDTO.builder()
                .cityId(profileCity.getCityId())
                .profileId(profileCity.getProfileId())
                .profile(new ProfileResponseDTO(
                        profile.getId(),
                        profile.getUsername(),
                        profile.getName()))

                .city(new CityResponseDTO(
                        city.getId(),
                        city.getName(),
                        city.getLatitude(),
                        city.getLongitude(),
                        WeatherStatus.valueOf(city.getWeatherStatus())
                )).build();

    }


}
