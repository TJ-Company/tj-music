package ru.tjcomp.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tjcomp.music.entity.UsersEntity;

public interface UsersRepository extends JpaRepository<UsersEntity, Long> {

    boolean existsByEmail(String email);
}
