package ru.tjcomp.music.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tjcomp.music.dto.TrackDto;
import ru.tjcomp.music.entity.Track;
import ru.tjcomp.music.entity.User;
import ru.tjcomp.music.repository.TrackRepository;
import ru.tjcomp.music.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class TrackService {

    private static final String FILE_PATH = "/hey/bro";
    private final UserRepository userRepository;
    private final TrackRepository trackRepository;

    public TrackDto addTrack(Long userId, TrackDto trackToAdd) {
        User userToGet = validateTrackToAdd(userId, trackToAdd);
        return saveTrack(userToGet, trackToAdd);
    }

    private User validateTrackToAdd(Long userId, TrackDto trackToAdd) {
        if (trackToAdd.id() != null) {
            throw new IllegalArgumentException("Id should be empty");
        }
        if (userId == null) {
            throw new IllegalArgumentException("Id should not be empty");
        }
        return userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("Not found user by id = " + userId));
    }

    private TrackDto saveTrack(User userToGet, TrackDto trackToAdd) {
        Track trackToSave = new Track(
            null,
            userToGet,
            trackToAdd.title(),
            FILE_PATH,
            trackToAdd.duration(),
            trackToAdd.createdAt());
        trackToSave = trackRepository.save(trackToSave);
        return toTrackDto(trackToSave);
    }

    public TrackDto getTrack(Long trackId) {
        Track trackToGet = trackRepository.findById(trackId)
            .orElseThrow(() -> new EntityNotFoundException("Not found track by id = " + trackId));
        return toTrackDto(trackToGet);
    }

    public List<TrackDto> getAllTracks() {
        List<Track> tracksToGet = trackRepository.findAll();
        return tracksToGet.stream().map(this::toTrackDto).toList();
    }

    public TrackDto updateTrack(Long trackId, TrackDto trackToUpdate) {
        Track trackEntity = trackRepository.findById(trackId)
            .orElseThrow(
                () -> new EntityNotFoundException("Not found track by id = " + trackId));

        Track entityToSave = new Track(
            trackEntity.getId(),
            trackEntity.getUser(),
            trackToUpdate.title(),
            trackEntity.getFilePath(),
            trackToUpdate.duration(),
            trackToUpdate.createdAt());
        Track updatedTrack = trackRepository.save(entityToSave);
        return toTrackDto(updatedTrack);
    }

    public void deleteTrack(Long trackId) {
        if (!trackRepository.existsById(trackId)){
            throw new EntityNotFoundException("Not found track by id = " + trackId);
        }
        trackRepository.deleteById(trackId);
    }

    private TrackDto toTrackDto(Track trackToSave) {
        return new TrackDto(
            trackToSave.getId(),
            trackToSave.getUser().getId(),
            trackToSave.getTitle(),
            trackToSave.getFilePath(),
            trackToSave.getDuration(),
            trackToSave.getCreatedAt());
    }
}
