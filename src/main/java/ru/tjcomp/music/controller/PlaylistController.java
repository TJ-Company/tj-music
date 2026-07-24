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
import ru.tjcomp.music.dto.PlaylistDto;
import ru.tjcomp.music.service.PlaylistService;

@RestController
@RequestMapping("/playlists")
@RequiredArgsConstructor
public class PlaylistController {

    private final Logger log = LoggerFactory.getLogger(PlaylistController.class);

    private final PlaylistService playlistService;

    @PostMapping("/{userId}")
    public ResponseEntity<PlaylistDto> addPlaylist(@PathVariable Long userId, @RequestBody @Valid PlaylistDto playlistToAdd){
        log.info("addPlaylist run");
        return ResponseEntity.status(201).body(playlistService.addPlaylist(userId, playlistToAdd));
    }

    @GetMapping("/{playlistId}")
    public ResponseEntity<PlaylistDto> getPlaylist(@PathVariable Long playlistId) {
        log.info("getPlaylist run");
        return ResponseEntity.status(200).body(playlistService.getPlaylist(playlistId));
    }

    @GetMapping
    public ResponseEntity<List<PlaylistDto>> getAllPlaylists() {
        log.info("getPlaylist run");
        return ResponseEntity.status(200).body(playlistService.getAllPlaylists());
    }

    @PutMapping("/{playlistId}")
    public ResponseEntity<PlaylistDto> updatePlaylist(@PathVariable Long playlistId,
        @RequestBody @Valid PlaylistDto playlistToUpdate) {
        log.info("updatePlaylist run");
        playlistService.updatePlaylist(playlistId, playlistToUpdate);
        return ResponseEntity.status(303).header("Redirection", "/playlists/success").build();
    }

    @GetMapping("/success")
    public ResponseEntity<String> success() {
        return ResponseEntity.status(200).body("successful update");
    }

    @DeleteMapping("/{playlistId}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable Long playlistId) {
        log.info("deletePlaylist run");
        playlistService.deletePlaylist(playlistId);
        return ResponseEntity.ok().build();
    }

}
