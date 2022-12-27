package com.iot.diagnostic.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.iot.diagnostic.dto.response.WeatherCurrentDto;
import com.iot.diagnostic.dto.response.WeatherDto;
import com.iot.diagnostic.dto.response.WeatherLocationDto;
import com.iot.diagnostic.dto.response.WeatherRequestDto;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class WeatherDtoMapper {

    private static final String IS_DAY_YES = "yes";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final ObjectMapper objectMapper;

    public WeatherDtoMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public WeatherDto mapJson(String json) {
        JsonNode jsonNode;
        try {
            jsonNode = this.objectMapper.readTree(json);
        } catch (JsonProcessingException ignored) {
            jsonNode = null;
        }
        if (Objects.isNull(jsonNode)) {
            return null;
        }
        final JsonNode weatherRequestNode = jsonNode.get("request");
        final JsonNode weatherLocationNode = jsonNode.get("location");
        final JsonNode weatherCurrentNode = jsonNode.get("current");
        final ObjectReader listReader = this.objectMapper.readerFor(new TypeReference<List<String>>() {});
        List<String> weatherIcons;
        List<String> weatherDescriptions;
        try {
            weatherIcons = listReader.readValue(weatherCurrentNode.get("weather_icons"));
            weatherDescriptions = listReader.readValue(weatherCurrentNode.get("weather_descriptions"));
        } catch (IOException ioe) {
            weatherIcons = Collections.emptyList();
            weatherDescriptions = Collections.emptyList();
        }
        return WeatherDto.builder()
                .weatherRequestDto(WeatherRequestDto.builder()
                        .type(weatherRequestNode.get("type").asText())
                        .query(weatherRequestNode.get("query").asText())
                        .language(weatherRequestNode.get("language").asText())
                        .unit(weatherRequestNode.get("unit").asText())
                        .build())
                .weatherLocationDto(WeatherLocationDto.builder()
                        .name(weatherLocationNode.get("name").asText())
                        .country(weatherLocationNode.get("country").asText())
                        .region(weatherLocationNode.get("region").asText())
                        .latitude(weatherLocationNode.get("lat").asDouble())
                        .longitude(weatherLocationNode.get("lon").asDouble())
                        .timezoneId(weatherLocationNode.get("timezone_id").asText())
                        .localTime(LocalDateTime.parse(weatherLocationNode.get("localtime").asText(), DATE_TIME_FORMATTER))
                        .utcOffset(weatherLocationNode.get("utc_offset").asDouble())
                        .build())
                .weatherCurrentDto(WeatherCurrentDto.builder()
                        .observationTime(weatherCurrentNode.get("observation_time").asText())
                        .temperature(weatherCurrentNode.get("temperature").asInt())
                        .weatherCode(weatherCurrentNode.get("weather_code").asInt())
                        .weatherIcons(weatherIcons)
                        .weatherDescriptions(weatherDescriptions)
                        .windSpeed(weatherCurrentNode.get("wind_speed").asInt())
                        .windDegree(weatherCurrentNode.get("wind_degree").asInt())
                        .windDirection(weatherCurrentNode.get("wind_dir").asText())
                        .pressure(weatherCurrentNode.get("pressure").asInt())
                        .precipitation(weatherCurrentNode.get("precip").asDouble())
                        .humidity(weatherCurrentNode.get("humidity").asInt())
                        .cloudCover(weatherCurrentNode.get("cloudcover").asInt())
                        .feelsLike(weatherCurrentNode.get("feelslike").asInt())
                        .uvIndex(weatherCurrentNode.get("uv_index").asInt())
                        .visibility(weatherCurrentNode.get("visibility").asInt())
                        .isDay(IS_DAY_YES.equalsIgnoreCase(weatherCurrentNode.get("is_day").asText()))
                        .build())
                .build();
    }

}
