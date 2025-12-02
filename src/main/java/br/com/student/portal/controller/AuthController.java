package br.com.student.portal.controller;

import br.com.student.portal.dto.user.UserRequest;
import br.com.student.portal.dto.user.UserResponse;
import br.com.student.portal.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * Registrar novo usuário
     * POST /api/auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest userRequest) {
        UserResponse registeredUser = authService.registerUser(userRequest);
        return ResponseEntity.status(CREATED).body(registeredUser);
    }

    /**
     * Fazer login - Autenticar usuário com matrícula e senha
     * POST /api/auth/login
     *
     * Body esperado:
     * {
     *   "registration": "2025001",
     *   "password": "Senha123!"
     * }
     */
    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserRequest loginRequest) {
        // ✅ Usar PasswordEncoder para verificar a senha de forma segura
        UserResponse user = authService.login(
                loginRequest.getRegistration(),
                loginRequest.getPassword()
        );
        return ResponseEntity.status(OK).body(user);
    }
}