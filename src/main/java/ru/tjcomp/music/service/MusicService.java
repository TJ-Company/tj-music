package ru.tjcomp.music.service;

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
