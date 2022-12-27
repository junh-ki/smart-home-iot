package com.iot.diagnostic.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    LocalDateTime localTime;
    double utcOffset;

}
