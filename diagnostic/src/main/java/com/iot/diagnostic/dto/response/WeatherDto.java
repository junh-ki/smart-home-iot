package com.iot.diagnostic.dto.response;

import lombok.Builder;

@Builder
public class WeatherDto {

    WeatherRequestDto weatherRequestDto;
    WeatherLocationDto weatherLocationDto;
    WeatherCurrentDto weatherCurrentDto;

}
