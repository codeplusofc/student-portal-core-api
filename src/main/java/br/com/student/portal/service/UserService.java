package br.com.student.portal.service;

import br.com.student.portal.dto.UserRequest;
import br.com.student.portal.dto.UserResponse;
import br.com.student.portal.entity.UserEntity;
import br.com.student.portal.exception.ObjectNotFoundException;
import br.com.student.portal.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static br.com.student.portal.validation.UserValidator.validateFields;

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

    public List<UserEntity> getAllUsers() {
        var users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new ObjectNotFoundException("No users found");
        }

        return users;
    }

    public UserEntity updateUser(UUID id, UserEntity userEntity) {
        var user = findUserById(id);

        validateFields(userEntity);

        user.setName(userEntity.getName());
        user.setEmail(userEntity.getEmail());
        user.setPassword(userEntity.getPassword());

        return userRepository.save(user);
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

