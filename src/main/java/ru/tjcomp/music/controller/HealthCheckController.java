package ru.tjcomp.music.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tjcomp.music.service.HealthCheckService;

@RestController
public class HealthCheckController {

    private static final Logger log = LoggerFactory.getLogger(HealthCheckController.class);

    private final HealthCheckService healthCheckService;

    public HealthCheckController(HealthCheckService healthCheckService){
        this.healthCheckService = healthCheckService;
    }

    @GetMapping()
    public String getStarted(){
        log.info("getStarted run");
        return healthCheckService.getStarted();
    }

    @GetMapping("/status")
    public String getStatus(){
        log.info("getStatus run");
        return healthCheckService.getStatus();
    }
}
