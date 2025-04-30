package br.com.student.portal.service;

import br.com.student.portal.entity.UserEntity;
import br.com.student.portal.exception.ObjectNotFoundException;
import br.com.student.portal.repository.StudentRepository;
import br.com.student.portal.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static br.com.student.portal.validation.UserValidator.validateFields;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    public UserService(UserRepository userRepository, StudentRepository studentRepository) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
    }

    public UserEntity createUser(UserEntity userEntity) {
        var student = studentRepository.findByRegistration(userEntity.getRegistration());

        if (student.isEmpty()) {
            throw new ObjectNotFoundException("Registration Not Found");
        }

        validateFields(userEntity);

        var user = new UserEntity();

        user.setName(userEntity.getName());
        user.setEmail(userEntity.getEmail());
        user.setPassword(userEntity.getPassword());
        user.setRegistration(userEntity.getRegistration());

        user.setStudent(student.get());

        return userRepository.save(user);
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

