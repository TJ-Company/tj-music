package ru.tjcomp.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tjcomp.music.entity.Track;

public interface TrackRepository extends JpaRepository<Track, Long> {

}
