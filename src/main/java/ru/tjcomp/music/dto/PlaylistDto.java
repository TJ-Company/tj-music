package ru.tjcomp.music.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

public record PlaylistDto(
    @Null
    Long id,
    @Null
    Long userId,
    @NotNull
    String name,
    @NotNull
    Boolean isPublic,
    @NotNull
    Boolean isFavourite,
    @PastOrPresent
    LocalDateTime createdAt
) {

}
