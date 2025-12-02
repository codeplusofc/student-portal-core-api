package br.com.student.portal.service;

import br.com.student.portal.dto.user.UserRequest;
import br.com.student.portal.dto.user.UserResponse;
import br.com.student.portal.entity.UserEntity;
import br.com.student.portal.exception.ObjectNotFoundException;
import br.com.student.portal.mapper.UserMapper;
import br.com.student.portal.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static br.com.student.portal.validation.UserValidator.validateFieldsUserRequest;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;  // ✅ Adicionar

    // ✅ ADICIONAR ESTE MÉTODO COMPLETO
    public UserResponse createUser(UserRequest userRequest) {
        // Validar campos
        validateFieldsUserRequest(userRequest);

        // Converter DTO para Entity usando o mapper
        UserEntity user = userMapper.userRequestIntoUserEntity(userRequest);

        // Criptografar a senha
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        // Salvar no banco de dados
        UserEntity savedUser = userRepository.save(user);

        // Retornar resposta
        return userMapper.userEntityIntoUserResponse(savedUser);
    }

    public List<UserResponse> getAllUsers() {
        var users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new ObjectNotFoundException("No users found");
        }
        return users.stream()
                .map(userMapper::userEntityIntoUserResponse)
                .collect(Collectors.toList());
    }

    public UserResponse updateUser(UUID id, UserRequest userRequest) {
        var user = findUserById(id);
        validateFieldsUserRequest(userRequest);

        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());

        // Se a senha foi fornecida, atualizar e criptografar
        if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }

        return userMapper.userEntityIntoUserResponse(userRepository.save(user));
    }

    public void deleteUser(UUID id) {
        var user = findUserById(id);
        userRepository.delete(user);
    }

    private UserEntity findUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("User with id " + id + " not found"));
    }

    public UserResponse getUserByRegistration(String registration) {
        UserEntity user = userRepository.findByRegistration(registration)
                .orElseThrow(() -> new ObjectNotFoundException("Usuário com matrícula " + registration + " não encontrado"));
        return userMapper.userEntityIntoUserResponse(user);
    }

}
