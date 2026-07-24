package ru.tjcomp.music.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
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
            FILE_PATH, 314L, LocalDateTime.of(2026, 4, 21, 16, 24));

        User user = User.builder()
            .id(userId)
            .username("name")
            .email("email")
            .passwordHash("password")
            .role(Role.USER)
            .createdAt(LocalDateTime.of(2026, 3, 21, 16, 24))
            .build();

        Track expectedTrack = Track.builder()
            .id(trackId)
            .author(user)
            .title(trackToAdd.title())
            .filePath(trackToAdd.filePath())
            .duration(trackToAdd.duration())
            .createdAt(trackToAdd.createdAt())
            .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(trackRepository.save(any(Track.class))).thenReturn(expectedTrack);

        TrackDto actualTrack;
        try {
            actualTrack = trackService.addTrack(userId, trackToAdd);
        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        }

        assertEquals(expectedTrack.getId(), actualTrack.id());
        assertEquals(expectedTrack.getAuthor().getId(), actualTrack.userId());
        assertEquals(expectedTrack.getTitle(), actualTrack.title());
        assertEquals(expectedTrack.getFilePath(), actualTrack.filePath());
        assertEquals(expectedTrack.getDuration(), actualTrack.duration());
        assertEquals(expectedTrack.getCreatedAt(), actualTrack.createdAt());

        verify(userRepository).findById(userId);
        verify(trackRepository).save(any(Track.class));
    }

    @Test
    void addTrack_IllegalArgumentException_WhenTrackIdNotNull() {
        Long userId = 1L;
        TrackDto trackToAdd = new TrackDto(1L, 3L, "Я смотрю аниме",
            FILE_PATH, 314L, LocalDateTime.of(2026, 4, 21, 16, 24));

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
            FILE_PATH, 314L, LocalDateTime.of(2026, 4, 21, 16, 24));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> trackService.addTrack(userId, trackToAdd));

        assertEquals("Id should not be empty", exception.getMessage());

        verify(userRepository, never()).findById(any());
        verify(trackRepository, never()).save(any());
    }

}