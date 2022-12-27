package com.iot.diagnostic.dto.response;

import java.util.List;

public record WeatherCurrentDto(String observationTime, int temperature, int weatherCode, List<String> weatherIcons,
                                List<String> weatherDescriptions, int windSpeed, int windDegree, String windDirection,
                                String pressure, double precipitation, int humidity, int cloudCover, int feelsLike,
                                int uvIndex, int visibility, boolean isDay) {}
