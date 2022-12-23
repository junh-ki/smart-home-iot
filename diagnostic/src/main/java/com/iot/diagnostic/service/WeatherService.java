package com.iot.diagnostic.service;

import com.iot.diagnostic.dto.WeatherDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@Service
public class WeatherService {

    private static final String API_HOST = "http://api.weatherstack.com/";
    private static final String ACCESS_KEY = "0e50bd055c1f4b1020b76e93780faacf";
    private final WebClient webClient;


    public WeatherService() {
        this.webClient = WebClient.builder()
                .baseUrl(API_HOST)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", API_HOST))
                .build();
    }

    public WeatherDto getCurrentWeatherByRegionName(String regionName) {
        //TODO: CONTINUE
        //this.webClient.get().uri()
        return null;
    }

}
