package com.iot.diagnostic.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class WeatherRequestDto {

    String type;
    String query;
    String language;
    String unit;

}
