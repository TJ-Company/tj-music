package ru.tjcomp.music.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
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
            LocalDate.of(2026, 3, 21));

        User resultUser = new User(
            100L,
            userToCreate.username(),
            userToCreate.email(),
            userToCreate.passwordHash(),
            userToCreate.role(),
            userToCreate.createdAt());

        when(userRepository.existsByEmail(any(String.class))).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(resultUser);

        UserDto testingUser = userService.createUser(userToCreate);

        assertEquals(resultUser.getId(), testingUser.id());
        assertEquals(resultUser.getUsername(), testingUser.username());
        assertEquals(resultUser.getEmail(), testingUser.email());
        assertEquals(resultUser.getPasswordHash(), testingUser.passwordHash());
        assertEquals(resultUser.getRole(), testingUser.role());
        assertEquals(resultUser.getCreatedAt(), testingUser.createdAt());

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
            LocalDate.of(2026, 3, 21));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> userService.createUser(userToCreate));

        assertEquals("Id should be empty", exception.getMessage());

        verify(userRepository, never()).existsByEmail(any());
    }
}