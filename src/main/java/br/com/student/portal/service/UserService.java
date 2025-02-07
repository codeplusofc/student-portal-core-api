package br.com.student.portal.service;

import br.com.student.portal.model.UserEntity;
import br.com.student.portal.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity createUser (UserEntity userEntity){
        return userRepository.save(userEntity);

    }

    public List<UserEntity> getAllUsers(){
        return userRepository.findAll();

    }

    public UserEntity updateUser(UUID id, UserEntity userEntityDetails){
        var user = userRepository.findById(id).orElseThrow();
        user.setName(userEntityDetails.getName());
        user.setEmail(userEntityDetails.getEmail());
        return userRepository.save(user);
    }

    public void deleteUser(UUID id){
        var user = userRepository.findById(id).orElseThrow();
        userRepository.delete(user);

    }


}

