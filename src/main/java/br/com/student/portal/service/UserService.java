package br.com.student.portal.service;

import br.com.student.portal.model.User;
import br.com.student.portal.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser (User user){
        var teste = userRepository.save(user);
        return teste;
    }
}
