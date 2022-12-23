package com.iot.diagnostic.controller;

import com.iot.diagnostic.service.SensorHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sensorHub")
public class SensorHubController {

    /*
    private final SensorHubService sensorHubService;

    @Autowired
    public SensorHubController(SensorHubService sensorHubService) {
        this.sensorHubService = sensorHubService;
    }

    @GetMapping("/boardInit")
    public Boolean isExternalBoardInitialized() {
        return this.sensorHubService.isExternalBoardInitialized();
    }
    */

}
