package com.company.test_atmos.dto.request;

import com.company.test_atmos.enums.WeatherStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CityWeatherStatusDTO {
    WeatherStatus status;
}
