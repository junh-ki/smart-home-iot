package com.iot.diagnostic.weather.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class WeatherCurrentDto {

    String observationTime;
    int temperature;
    int weatherCode;
    List<String> weatherIcons;
    List<String> weatherDescriptions;
    int windSpeed;
    int windDegree;
    String windDirection;
    int pressure;
    double precipitation;
    int humidity;
    int cloudCover;
    int feelsLike;
    int uvIndex;
    int visibility;
    boolean isDay;

}
