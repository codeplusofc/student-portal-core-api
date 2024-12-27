package br.com.student.portal.service;

import br.com.student.portal.model.User;
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

    public User createUser (User user){
        return userRepository.save(user);

    }

    public List<User> getAllUsers(){
        return userRepository.findAll();

    }

    public User updateUser(UUID id, User userDetails){
        var user = userRepository.findById(id).orElseThrow();
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        return userRepository.save(user);
    }

    public void deleteUser(UUID id){
        var user = userRepository.findById(id).orElseThrow();
        userRepository.delete(user);

    }


}

