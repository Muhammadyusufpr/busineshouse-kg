package com.company.test_atmos.dto.request;

import com.company.test_atmos.enums.WeatherStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CityRequestDTO {
    String name;
    Double latitude;
    Double longitude;
    WeatherStatus weatherStatus;



}
