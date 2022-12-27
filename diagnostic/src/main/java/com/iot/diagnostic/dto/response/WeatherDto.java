package com.iot.diagnostic.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class WeatherDto {

    WeatherRequestDto weatherRequestDto;
    WeatherLocationDto weatherLocationDto;
    WeatherCurrentDto weatherCurrentDto;

}
