package com.iot.diagnostic.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class WeatherLocationDto {

    String name;
    String country;
    String region;
    double latitude;
    double longitude;
    String timezoneId;
    LocalDateTime localTime;
    double utcOffset;

}
