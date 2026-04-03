package ru.tjcomp.music;

import org.springframework.stereotype.Service;

@Service
public class MusicService {

    public String getStarted() {
        return "Hello World!";
    }

    public String getStatus() {
        return "Server is running";
    }
}
