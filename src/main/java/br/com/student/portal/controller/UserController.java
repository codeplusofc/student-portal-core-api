package br.com.student.portal.controller;


import br.com.student.portal.dto.UserRequest;
import br.com.student.portal.dto.UserResponse;
import br.com.student.portal.entity.UserEntity;
import br.com.student.portal.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        var user = userService.createUser(userRequest);
        return ResponseEntity.status(CREATED).body(user);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        var users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable UUID id, @RequestBody UserRequest userEntityDetails) {
        var updatedUser = userService.updateUser(id, userEntityDetails);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
