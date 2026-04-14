package ru.tjcomp.music.dto;

import java.time.LocalDate;
import ru.tjcomp.music.enums.Role;

public record UsersDto(
    Long id,
    String username,
    String email,
    String passwordHash,
    Role role,
    LocalDate createdAt
) {

}