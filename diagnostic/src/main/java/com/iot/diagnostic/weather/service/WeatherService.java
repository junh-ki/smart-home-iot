package com.iot.diagnostic.weather.service;

import com.iot.diagnostic.commons.redis.RedisKeys;
import com.iot.diagnostic.commons.redis.service.RedisService;
import com.iot.diagnostic.weather.dto.response.WeatherDto;
import com.iot.diagnostic.weather.mapper.WeatherDtoMapper;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
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
    private final RedisService redisService;
    private final WeatherDtoMapper weatherDtoMapper;

    public WeatherService(WebClient.Builder webClientBuilder, RedisService redisService,
                          WeatherDtoMapper weatherDtoMapper) {
        this.webClient = webClientBuilder.build();
        this.redisService = redisService;
        this.weatherDtoMapper = weatherDtoMapper;
    }

    public WeatherDto getCurrentWeatherByRegionName(String regionName) {
        final String redisKey = getRedisKeyForCurrentWeather(regionName);
        final String currentWeatherStr = this.redisService.get(redisKey);
        if (StringUtils.isNotBlank(currentWeatherStr)) {
            //TODO: translate currentWeatherStr into WeatherDto
            return null;
        }
        final MultiValueMap<String, String> parameterMap = new LinkedMultiValueMap<>();
        parameterMap.put(PARAM_ACCESS_KEY, Collections.singletonList(ACCESS_KEY));
        parameterMap.put(PARAM_REGION_NAME, Collections.singletonList(regionName));
        final URI uri = UriComponentsBuilder
                .fromHttpUrl(API_HOST + WeatherDataType.CURRENT.getUrlPath())
                .queryParams(parameterMap)
                .build()
                .toUri();
        final String weatherCurrentResponseJson = this.webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        final WeatherDto currentWeather = this.weatherDtoMapper.mapJson(weatherCurrentResponseJson);
        //TODO: convert currentWeather to String
        //this.redisService.set(redisKey, currentWeather);
        return currentWeather;
    }

    private String getRedisKeyForCurrentWeather(String regionName) {
        return RedisKeys.WEATHER_CURRENT + regionName.replace(" ", "-").toLowerCase();
    }

}
