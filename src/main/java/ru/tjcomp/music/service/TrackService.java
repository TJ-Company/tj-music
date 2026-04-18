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
        if (userId == null) {
            throw new IllegalArgumentException("Id should be empty");
        }

        User userToGet = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("Not found user by id = " + userId));

        Track trackToSave = new Track(
            null,
            userToGet,
            trackToAdd.title(),
            FILE_PATH,
            trackToAdd.duration(),
            trackToAdd.createdAt());
        trackRepository.save(trackToSave);
        return toTrackDto(trackToSave);
    }

    public TrackDto getTrack(Long id) {
        Track trackToGet = trackRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Not found track by id = " + id));
        return toTrackDto(trackToGet);
    }

    public List<TrackDto> getAllTracks() {
        List<Track> tracksToGet = trackRepository.findAll();
        return tracksToGet.stream().map(this::toTrackDto).toList();
    }

    public TrackDto updateTrack(Long id, TrackDto trackToUpdate) {
        Track trackEntity = trackRepository.findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("Not found track by id = " + id));

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

    public void deleteTrack(Long id) {
        if (!trackRepository.existsById(id)){
            throw new EntityNotFoundException("Not found track by id = " + id);
        }
        trackRepository.deleteById(id);
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
