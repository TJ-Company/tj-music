package ru.tjcomp.music.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tjcomp.music.dto.TrackDto;
import ru.tjcomp.music.service.TrackService;

@RestController
@RequestMapping("/tracks")
@RequiredArgsConstructor
public class TrackController {
    private final Logger log = LoggerFactory.getLogger(TrackController.class);
    private final TrackService trackService;

    @PostMapping("/{id}")
    public ResponseEntity<TrackDto> addTrack(@PathVariable Long id, @RequestBody @Valid TrackDto trackToAdd){
        log.info("addTrack run");
        return ResponseEntity.status(201).body(trackService.addTrack(id, trackToAdd));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrackDto> getTrack(@PathVariable("id") Long id) {
        log.info("getTrack run");
        return ResponseEntity.status(200).body(trackService.getTrack(id));
    }

    @GetMapping
    public ResponseEntity<List<TrackDto>> getTrack() {
        log.info("getTrack run");
        return ResponseEntity.status(200).body(trackService.getAllTracks());
    }

    @PutMapping("{id}")
    public ResponseEntity<TrackDto> updateTrack(@PathVariable Long id,
        @RequestBody @Valid TrackDto trackToUpdate) {
        log.info("updateTrack run");
        trackService.updateTrack(id, trackToUpdate);
        return ResponseEntity.status(303).header("Redirection", "/tracks/success").build();
    }

    @GetMapping("/success")
    public ResponseEntity<String> success() {
        return ResponseEntity.status(200).body("successful update");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrack(@PathVariable Long id) {
        log.info("deleteTrack run");
        trackService.deleteTrack(id);
        return ResponseEntity.ok().build();
    }
}
