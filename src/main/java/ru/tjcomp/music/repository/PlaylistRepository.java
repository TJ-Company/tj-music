package ru.tjcomp.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tjcomp.music.entity.Playlist;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

}
