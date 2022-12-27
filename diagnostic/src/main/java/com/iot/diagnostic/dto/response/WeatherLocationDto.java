package com.iot.diagnostic.dto.response;

import java.time.LocalDateTime;

public record WeatherLocationDto(String name, String country, String region, double latitude, double longitude,
                                 String timezoneId, LocalDateTime localTime, double utcOffset) {}
