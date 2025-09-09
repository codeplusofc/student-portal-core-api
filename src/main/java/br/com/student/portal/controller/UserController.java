package br.com.student.portal.controller;


import br.com.student.portal.dto.user.UserRequest;
import br.com.student.portal.dto.user.UserResponse;
import br.com.student.portal.entity.FileEntity;
import br.com.student.portal.service.FileService;
import br.com.student.portal.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.OK;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("api/users")
public class UserController {

    private final FileService fileService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.status(FOUND).body(userService.getAllUsers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable UUID id, @RequestBody UserRequest userEntityDetails) {
        return ResponseEntity.status(OK).body(userService.updateUser(id, userEntityDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/access")
    public ResponseEntity<List<FileEntity>> accessVideos(@PathVariable UUID id){
            return ResponseEntity.status(FOUND).body(fileService.getAllFiles(id));
        }
    }

