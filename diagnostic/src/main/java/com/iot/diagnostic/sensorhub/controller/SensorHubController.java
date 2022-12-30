package com.iot.diagnostic.sensorhub.controller;

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
