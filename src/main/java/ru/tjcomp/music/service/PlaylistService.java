package ru.tjcomp.music.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tjcomp.music.dto.PlaylistDto;
import ru.tjcomp.music.entity.Playlist;
import ru.tjcomp.music.entity.User;
import ru.tjcomp.music.repository.PlaylistRepository;
import ru.tjcomp.music.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;

    public PlaylistDto addPlaylist(Long userId, PlaylistDto playlistToAdd) {
        User userToGet = validatePlaylistInput(userId, playlistToAdd);
        return savePlaylist(userToGet, playlistToAdd);
    }

    private User validatePlaylistInput(Long userId, PlaylistDto playlistToAdd) {
        if (playlistToAdd.id() != null) {
            throw new IllegalArgumentException("Id should be empty");
        }
        if (userId == null) {
            throw new IllegalArgumentException("Id should not be empty");
        }

        return userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("Not found user by id = " + userId));
    }

    private PlaylistDto savePlaylist(User userToGet, PlaylistDto playlistToAdd) {
        Playlist playlistToSave = Playlist.builder()
            .id(null)
            .user(userToGet)
            .name(playlistToAdd.name())
            .isPublic(playlistToAdd.isPublic())
            .isFavourite(playlistToAdd.isFavourite())
            .createdAt(playlistToAdd.createdAt())
            .build();
        playlistToSave = playlistRepository.save(playlistToSave);
        return toPlaylistDto(playlistToSave);
    }

    public PlaylistDto getPlaylist(Long playlistId) {
        Playlist playlistToGet = playlistRepository.findById(playlistId)
            .orElseThrow(() -> new EntityNotFoundException("Not found playlist by id = " + playlistId));
        return toPlaylistDto(playlistToGet);
    }

    public List<PlaylistDto> getAllPlaylists() {
        List<Playlist> playlistToGet = playlistRepository.findAll();
        return playlistToGet.stream().map(this::toPlaylistDto).toList();
    }

    public PlaylistDto updatePlaylist(Long playlistId, PlaylistDto playlistToUpdate) {
        Playlist playlistEntity = playlistRepository.findById(playlistId)
            .orElseThrow(
                () -> new EntityNotFoundException("Not found playlist by id = " + playlistId));

        Playlist entityToSave = Playlist.builder()
            .id(playlistEntity.getId())
            .user(playlistEntity.getUser())
            .name(playlistToUpdate.name())
            .isPublic(playlistToUpdate.isPublic())
            .isFavourite(playlistToUpdate.isFavourite())
            .createdAt(playlistToUpdate.createdAt())
            .build();

        Playlist updatedPlaylist = playlistRepository.save(entityToSave);
        return toPlaylistDto(updatedPlaylist);
    }

    public void deletePlaylist(Long playlistId) {
        if (!playlistRepository.existsById(playlistId)) {
            throw new EntityNotFoundException("Not found playlist by id = " + playlistId);
        }
        playlistRepository.deleteById(playlistId);
    }

    private PlaylistDto toPlaylistDto(Playlist playlistToSave) {
        return new PlaylistDto(
            playlistToSave.getId(),
            playlistToSave.getUser().getId(),
            playlistToSave.getName(),
            playlistToSave.getIsPublic(),
            playlistToSave.getIsFavourite(),
            playlistToSave.getCreatedAt());
    }
}
