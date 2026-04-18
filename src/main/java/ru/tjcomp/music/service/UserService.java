package ru.tjcomp.music.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tjcomp.music.dto.UserDto;
import ru.tjcomp.music.entity.User;
import ru.tjcomp.music.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto createUser(UserDto userToCreate) {
        if (userToCreate.id() != null) {
            throw new IllegalArgumentException("Id should be empty");
        }
        if (userRepository.existsByEmail(userToCreate.email())) {
            throw new IllegalArgumentException("Such an account already exists");
        }
        User entityToSave = new User(
            null,
            userToCreate.username(),
            userToCreate.email(),
            userToCreate.passwordHash(),
            userToCreate.role(),
            userToCreate.createdAt());
        User savedEntity = userRepository.save(entityToSave);
        return toUserDto(savedEntity);
    }

    public UserDto getUser(Long id) {
        User userToGet = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Not found user by id = " + id));
        return toUserDto(userToGet);
    }

    public List<UserDto> getAllUsers() {
        List<User> usersToGet = userRepository.findAll();
        return usersToGet.stream().map(this::toUserDto).toList();
    }

    public UserDto updateUser(Long id, UserDto userToUpdate) {
        User userEntity = userRepository.findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("Not found user by id = " + id));

        User entityToSave = new User(
            userEntity.getId(),
            userToUpdate.username(),
            userToUpdate.email(),
            userToUpdate.passwordHash(),
            userToUpdate.role(),
            userToUpdate.createdAt());
        User updatedUser = userRepository.save(entityToSave);
        return toUserDto(updatedUser);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)){
            throw new EntityNotFoundException("Not found user by id = " + id);
        }
        userRepository.deleteById(id);
    }

    private UserDto toUserDto(User User) {
        return new UserDto(
            User.getId(),
            User.getUsername(),
            User.getEmail(),
            User.getPasswordHash(),
            User.getRole(),
            User.getCreatedAt()
        );
    }
}
