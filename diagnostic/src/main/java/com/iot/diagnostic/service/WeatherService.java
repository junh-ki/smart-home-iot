package com.iot.diagnostic.service;

import com.iot.diagnostic.dto.response.WeatherDto;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;

@Service
public class WeatherService {

    private enum WeatherDataType {

        CURRENT("current"),
        HISTORICAL("historical"),
        FORECAST("forecast"),
        LOCATION_LOOKUP("autocomplete");

        @Getter
        private final String urlPath;

        WeatherDataType(String urlPath) {
            this.urlPath = urlPath;
        }

    }

    private static final String API_HOST = "http://api.weatherstack.com/";
    private static final String PARAM_ACCESS_KEY = "access_key";
    private static final String PARAM_REGION_NAME = "query";
    private static final String ACCESS_KEY = "0e50bd055c1f4b1020b76e93780faacf";
    private final WebClient webClient;


    public WeatherService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public WeatherDto getCurrentWeatherByRegionName(String regionName) {
        final MultiValueMap<String, String> parameterMap = new LinkedMultiValueMap<>();
        parameterMap.put(PARAM_ACCESS_KEY, Collections.singletonList(ACCESS_KEY));
        parameterMap.put(PARAM_REGION_NAME, Collections.singletonList(regionName));
        final URI uri = UriComponentsBuilder
                .fromHttpUrl(API_HOST + WeatherDataType.CURRENT.getUrlPath())
                .queryParams(parameterMap)
                .build()
                .toUri();
        return this.webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(WeatherDto.class)
                .block();
    }

}
