package br.com.student.portal.controller;


import br.com.student.portal.dto.user.UserRequest;
import br.com.student.portal.dto.user.UserResponse;
import br.com.student.portal.entity.UserEntity;
import br.com.student.portal.security.AuthorizationService;
import br.com.student.portal.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@CrossOrigin
@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;
    private final AuthController authController;

    public UserController(UserService userService, AuthController authController) {
        this.userService = userService;
        this.authController = authController;
    }

    @Operation(summary = "Create User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created with sucessful",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))),
            @ApiResponse(responseCode = "400", description = "User already exists")})

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        var user = authController.createUser(userRequest);
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
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
