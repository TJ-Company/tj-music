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
        validateUserToCreate(userToCreate);
        return saveUser(userToCreate);
    }

    private void validateUserToCreate(UserDto userToCreate) {
        if (userToCreate.id() != null) {
            throw new IllegalArgumentException("Id should be empty");
        }
        if (userRepository.existsByEmail(userToCreate.email())) {
            throw new IllegalArgumentException("Such an account already exists");
        }
    }

    private UserDto saveUser(UserDto userToCreate) {
        User entityToSave = User.builder()
            .id(null)
            .username(userToCreate.username())
            .email(userToCreate.email())
            .passwordHash(userToCreate.passwordHash())
            .role(userToCreate.role())
            .createdAt(userToCreate.createdAt())
            .build();
        User savedEntity = userRepository.save(entityToSave);
        return toUserDto(savedEntity);
    }

    public UserDto getUser(Long userId) {
        User userToGet = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("Not found user by id = " + userId));
        return toUserDto(userToGet);
    }

    public List<UserDto> getAllUsers() {
        List<User> usersToGet = userRepository.findAll();
        return usersToGet.stream().map(this::toUserDto).toList();
    }

    public UserDto updateUser(Long userId, UserDto userToUpdate) {
        User userEntity = userRepository.findById(userId)
            .orElseThrow(
                () -> new EntityNotFoundException("Not found user by id = " + userId));

        User entityToSave = User.builder()
            .id(userEntity.getId())
            .username(userToUpdate.username())
            .email(userToUpdate.email())
            .passwordHash(userToUpdate.passwordHash())
            .role(userToUpdate.role())
            .createdAt(userToUpdate.createdAt())
            .build();

        User updatedUser = userRepository.save(entityToSave);
        return toUserDto(updatedUser);
    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("Not found user by id = " + userId);
        }
        userRepository.deleteById(userId);
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
