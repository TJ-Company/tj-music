package ru.tjcomp.music.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;

public record TrackDto(
    @Null
    Long id,
    @Null
    Long userId,
    @NotNull
    String title,
    @Null
    String filePath,
    @NotNull
    Long duration,
    @PastOrPresent
    LocalDate createdAt
) {

}
