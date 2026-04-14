package ru.tjcomp.music.controller;

import java.util.List;
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
import ru.tjcomp.music.dto.UsersDto;
import ru.tjcomp.music.service.UsersService;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final Logger log = LoggerFactory.getLogger(UsersController.class);

    private final UsersService usersService;

    public UsersController(UsersService usersService){
        this.usersService = usersService;
    }

    @PostMapping()
    public ResponseEntity<UsersDto> addUser(@RequestBody UsersDto userToAdd){
        log.info("createUser run");
        return ResponseEntity.status(201).body(usersService.createUser(userToAdd));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsersDto> getUser(@PathVariable("id") Long id){
        log.info("getUser run");
        return ResponseEntity.status(200).body(usersService.getUser(id));
    }

    @GetMapping()
    public ResponseEntity<List<UsersDto>> getUser(){
        log.info("getUser run");
        return ResponseEntity.status(200).body(usersService.getAllUsers());
    }

    @PutMapping()
    public ResponseEntity<UsersDto> updateUser(@RequestBody UsersDto userToUpdate){
        log.info("updateUser run");
        var updatedUser = usersService.updateUser(userToUpdate);
        return ResponseEntity.status(200).body(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        log.info("deleteUser run");
        usersService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
