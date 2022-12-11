package com.iot.diagnostic.controller;

import com.iot.diagnostic.service.SensorHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class SensorHubController {

    private final SensorHubService sensorHubService;

    @Autowired
    public SensorHubController(SensorHubService sensorHubService) {
        this.sensorHubService = sensorHubService;
    }

}
