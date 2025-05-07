package br.com.student.portal.service;

import br.com.student.portal.dto.UserRequest;
import br.com.student.portal.dto.UserResponse;
import br.com.student.portal.entity.UserEntity;
import br.com.student.portal.exception.ObjectNotFoundException;
import br.com.student.portal.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static br.com.student.portal.validation.UserValidator.validateFields;
import static br.com.student.portal.validation.UserValidator.validateFieldsUserRequest;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse createUser(UserRequest userRequest) {
        var userEntity = new UserEntity(userRequest.getName(), userRequest.getEmail(), userRequest.getPassword());
        validateFields(userEntity);
        var userSaved = userRepository.save(userEntity);
        return new UserResponse(userSaved.getId(), userSaved.getName(), userSaved.getEmail());

    }

    public List<UserResponse> getAllUsers() {
        var users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new ObjectNotFoundException("No users found");
        }

        return users.stream()
                .map(userEntity -> new UserResponse(userEntity.getId() , userEntity.getName() , userEntity.getEmail() ))
                .collect(Collectors.toList());
    }

    public UserResponse updateUser(UUID id, UserRequest userRequest) {
        var user = findUserById(id);

        validateFieldsUserRequest(userRequest);

        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        var userSaved = userRepository.save(user);

        return new UserResponse(userSaved.getId(), userSaved.getName(), userSaved.getEmail());

    }

    public void deleteUser(UUID id) {
        var user = findUserById(id);

        userRepository.delete(user);
    }

    private UserEntity findUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("User with id " + id + " not found"));
    }
}

