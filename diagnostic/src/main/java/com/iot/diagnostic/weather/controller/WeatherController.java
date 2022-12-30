package com.iot.diagnostic.weather.controller;

import com.iot.diagnostic.weather.dto.response.WeatherDto;
import com.iot.diagnostic.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/current/{regionName}")
    public WeatherDto getCurrentWeather(@PathVariable("regionName") String regionName) {
        return this.weatherService.getCurrentWeatherByRegionName(regionName);
    }

}
