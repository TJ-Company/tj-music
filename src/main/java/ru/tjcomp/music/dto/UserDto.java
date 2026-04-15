package ru.tjcomp.music.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import java.time.LocalDate;
import ru.tjcomp.music.enums.Role;

public record UserDto(
    @Null
    Long id,
    @NotNull
    String username,
    @NotNull
    String email,
    @NotNull
    String passwordHash,
    @NotNull
    Role role,
    @FutureOrPresent
    LocalDate createdAt
) {

}