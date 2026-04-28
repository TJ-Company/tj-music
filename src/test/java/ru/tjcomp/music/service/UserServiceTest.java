package ru.tjcomp.music.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tjcomp.music.dto.UserDto;
import ru.tjcomp.music.entity.User;
import ru.tjcomp.music.enums.Role;
import ru.tjcomp.music.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_SaveAndReturnUser_WhenValidDataProvided() {

        UserDto userToCreate = new UserDto(
            null,
            "name",
            "email",
            "password",
            Role.USER,
            LocalDateTime.of(2026, 3, 21, 14, 12));

        User expectedUser = User.builder()
            .id(100L)
            .username(userToCreate.username())
            .email(userToCreate.email())
            .passwordHash(userToCreate.passwordHash())
            .role(userToCreate.role())
            .createdAt(userToCreate.createdAt())
            .build();

        when(userRepository.existsByEmail(any(String.class))).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);

        UserDto actualUser = userService.createUser(userToCreate);

        assertEquals(expectedUser.getId(), actualUser.id());
        assertEquals(expectedUser.getUsername(), actualUser.username());
        assertEquals(expectedUser.getEmail(), actualUser.email());
        assertEquals(expectedUser.getPasswordHash(), actualUser.passwordHash());
        assertEquals(expectedUser.getRole(), actualUser.role());
        assertEquals(expectedUser.getCreatedAt(), actualUser.createdAt());

        verify(userRepository).existsByEmail(any(String.class));
        verify(userRepository).save(any(User.class));
    }

    @Test
    void createUser_IllegalArgumentException_WhenUserIdNotNull() {

        UserDto userToCreate = new UserDto(
            100L,
            "name",
            "email",
            "password",
            Role.USER,
            LocalDateTime.of(2026, 3, 21, 14, 13));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> userService.createUser(userToCreate));

        assertEquals("Id should be empty", exception.getMessage());

        verify(userRepository, never()).existsByEmail(any());
    }
}