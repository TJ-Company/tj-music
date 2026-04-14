package ru.tjcomp.music.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import ru.tjcomp.music.dto.UsersDto;
import ru.tjcomp.music.entity.UsersEntity;
import ru.tjcomp.music.repository.UsersRepository;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public UsersDto createUser(UsersDto userToCreate) {
        if (userToCreate.id() != null) {
            throw new IllegalArgumentException("Id should be empty");
        }
        if (usersRepository.existsByEmail(userToCreate.email())) {
            throw new IllegalArgumentException("Such an account already exists");
        }
        var entityToSave = new UsersEntity(
            null,
            userToCreate.username(),
            userToCreate.email(),
            userToCreate.passwordHash(),
            userToCreate.role(),
            userToCreate.createdAt());
        var savedEntity = usersRepository.save(entityToSave);
        return toUsersDto(savedEntity);
    }

    public UsersDto getUser(Long id) {
        UsersEntity userToGet = usersRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Not found user by id = " + id));
        return toUsersDto(userToGet);
    }

    public List<UsersDto> getAllUsers() {
        List<UsersEntity> usersToGet = usersRepository.findAll();
        return usersToGet.stream().map(this::toUsersDto).toList();
    }

    public UsersDto updateUser(UsersDto usersToUpdate) {
        var userEntity = usersRepository.findById(usersToUpdate.id())
            .orElseThrow(
                () -> new EntityNotFoundException("Not found user by id = " + usersToUpdate.id()));

        var entityToSave = new UsersEntity(
            userEntity.getId(),
            usersToUpdate.username(),
            usersToUpdate.email(),
            usersToUpdate.passwordHash(),
            usersToUpdate.role(),
            usersToUpdate.createdAt());
        var updatedUser = usersRepository.save(entityToSave);
        return toUsersDto(updatedUser);
    }

    public void deleteUser(Long id) {
        if (!usersRepository.existsById(id)){
            throw new EntityNotFoundException("Not found user by id = " + id);
        }
        usersRepository.deleteById(id);
    }

    private UsersDto toUsersDto(UsersEntity UsersEntity) {
        return new UsersDto(
            UsersEntity.getId(),
            UsersEntity.getUsername(),
            UsersEntity.getEmail(),
            UsersEntity.getPasswordHash(),
            UsersEntity.getRole(),
            UsersEntity.getCreatedAt()
        );
    }
}
