package ru.tjcomp.music.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tjcomp.music.dto.UserDto;
import ru.tjcomp.music.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userToAdd) {
        log.info("createUser run");
        return ResponseEntity.status(201).body(userService.createUser(userToAdd));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") Long id) {
        log.info("getUser run");
        return ResponseEntity.status(200).body(userService.getUser(id));
    }

    @GetMapping()
    public ResponseEntity<List<UserDto>> getUser() {
        log.info("getUser run");
        return ResponseEntity.status(200).body(userService.getAllUsers());
    }

    @PutMapping()
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userToUpdate) {
        log.info("updateUser run");
        userService.updateUser(userToUpdate);
        return ResponseEntity.status(303).header("Redirection", "/success").build();
    }

    @GetMapping("/success")
    public ResponseEntity<String> success() {
        return ResponseEntity.status(200).body("successful update");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("deleteUser run");
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
