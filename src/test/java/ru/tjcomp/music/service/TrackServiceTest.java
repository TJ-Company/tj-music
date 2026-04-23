package ru.tjcomp.music.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tjcomp.music.dto.TrackDto;
import ru.tjcomp.music.entity.Track;
import ru.tjcomp.music.entity.User;
import ru.tjcomp.music.enums.Role;
import ru.tjcomp.music.repository.TrackRepository;
import ru.tjcomp.music.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class TrackServiceTest {

    @Mock
    private TrackRepository trackRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TrackService trackService;

    private static final String FILE_PATH = "/hey/bro";

    @Test
    void addTrack_SaveAndReturnTrack_WhenValidDataProvided() {
        Long userId = 2L;
        Long trackId = 3L;
        TrackDto trackToAdd = new TrackDto(null, userId, "Я смотрю аниме",
            FILE_PATH, 314L, LocalDate.of(2026, 4, 21));

        User user = new User(
            userId,
            "name",
            "email",
            "password",
            Role.USER,
            LocalDate.of(2026, 3, 21));

        Track resultTrack = new Track(
            trackId,
            user,
            trackToAdd.title(),
            trackToAdd.filePath(),
            trackToAdd.duration(),
            trackToAdd.createdAt());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(trackRepository.save(any(Track.class))).thenReturn(resultTrack);

        TrackDto testingTrack = trackService.addTrack(userId, trackToAdd);

        assertEquals(resultTrack.getId(), testingTrack.id());
        assertEquals(resultTrack.getUser().getId(), testingTrack.userId());
        assertEquals(resultTrack.getTitle(), testingTrack.title());
        assertEquals(resultTrack.getFilePath(), testingTrack.filePath());
        assertEquals(resultTrack.getDuration(), testingTrack.duration());
        assertEquals(resultTrack.getCreatedAt(), testingTrack.createdAt());

        verify(userRepository).findById(userId);
        verify(trackRepository).save(any(Track.class));
    }

    @Test
    void addTrack_IllegalArgumentException_WhenTrackIdNotNull() {
        Long userId = 1L;
        TrackDto trackToAdd = new TrackDto(1L, 3L, "Я смотрю аниме",
            FILE_PATH, 314L, LocalDate.of(2026, 4, 21));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> trackService.addTrack(userId, trackToAdd));

        assertEquals("Id should be empty", exception.getMessage());

        verify(userRepository, never()).findById(any());
        verify(trackRepository, never()).save(any());
    }

    @Test
    void addTrack_IllegalArgumentException_WhenUserIdNull() {
        Long userId = null;
        TrackDto trackToAdd = new TrackDto(null, 3L, "Я смотрю аниме",
            FILE_PATH, 314L, LocalDate.of(2026, 4, 21));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> trackService.addTrack(userId, trackToAdd));

        assertEquals("Id should not be empty", exception.getMessage());

        verify(userRepository, never()).findById(any());
        verify(trackRepository, never()).save(any());
    }

}