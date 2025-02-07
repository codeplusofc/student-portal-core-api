package br.com.student.portal.controller;


import br.com.student.portal.model.UserEntity;
import br.com.student.portal.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity userEntity){
        var newUser = userService.createUser(userEntity);
        return new ResponseEntity<>(newUser, CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        var users = userService.getAllUsers();
        return new ResponseEntity<>(users, OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable UUID id, @RequestBody UserEntity userEntityDetails){
        var updateUser = userService.updateUser(id, userEntityDetails);
        return new ResponseEntity<>(updateUser, OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id){
        userService.deleteUser(id);
        return new ResponseEntity<>(NO_CONTENT);
    }
}
