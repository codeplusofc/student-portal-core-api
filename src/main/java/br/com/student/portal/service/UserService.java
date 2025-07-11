package br.com.student.portal.service;

import br.com.student.portal.dto.user.UserRequest;
import br.com.student.portal.dto.user.UserResponse;
import br.com.student.portal.entity.UserEntity;
import br.com.student.portal.exception.ObjectNotFoundException;
import br.com.student.portal.mapper.UserMapper;
import br.com.student.portal.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import static br.com.student.portal.validation.UserValidator.validateFields;
import static br.com.student.portal.validation.UserValidator.validateFieldsUserRequest;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserResponse createUser(UserRequest userRequest) {
        var userEntity = userMapper.userRequestIntoUserEntity(userRequest);

        validateFields(userEntity);

        return userMapper.userEntityIntoUserResponse(userRepository.save(userEntity));

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
        user.setPassword(userRequest.getPassword());

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
}



