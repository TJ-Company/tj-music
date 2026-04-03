package ru.tjcomp.music;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MusicController {

    private final MusicService musicService;

    public MusicController(MusicService musicService){
        this.musicService = musicService;
    }

    @GetMapping()
    public String getStarted(){
        return musicService.getStarted();
    }

    @GetMapping("/status")
    public String getStatus(){
        return musicService.getStatus();
    }
}
