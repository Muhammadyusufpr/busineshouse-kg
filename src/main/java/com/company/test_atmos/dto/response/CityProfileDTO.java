package com.company.test_atmos.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class  CityProfileDTO {
    String cityId;
    String profileId;
    String username;
    String cityName;


    public static CityProfileDTO toDTO(CityProfileMapperDTO mapper) {
        return CityProfileDTO.builder()
                .cityId(mapper.getCityId())
                .profileId(mapper.getProfileId())
                .username(mapper.getUsername())
                .cityName(mapper.getCityName())
                .build();
    }

}
