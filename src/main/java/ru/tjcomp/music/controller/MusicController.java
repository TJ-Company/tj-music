package ru.tjcomp.music.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tjcomp.music.service.MusicService;

@RestController
public class MusicController {

    private static final Logger log = LoggerFactory.getLogger(MusicController.class);
    private final MusicService musicService;

    public MusicController(MusicService musicService){
        this.musicService = musicService;
    }

    @GetMapping()
    public String getStarted(){
        log.info("getStarted run");
        return musicService.getStarted();
    }

    @GetMapping("/status")
    public String getStatus(){
        log.info("getStatus run");
        return musicService.getStatus();
    }
}
