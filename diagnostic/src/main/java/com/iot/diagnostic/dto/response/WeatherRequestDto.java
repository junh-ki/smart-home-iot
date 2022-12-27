package com.iot.diagnostic.dto.response;

public record WeatherRequestDto(String type, String query, String language, String unit) {}
