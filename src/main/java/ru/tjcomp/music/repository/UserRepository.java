package ru.tjcomp.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tjcomp.music.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
}
