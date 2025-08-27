package br.com.student.portal.security;


import br.com.student.portal.dto.user.UserRequest;
import br.com.student.portal.dto.user.UserResponse;
import br.com.student.portal.entity.UserEntity;
import br.com.student.portal.exception.BadRequestException;
import br.com.student.portal.mapper.UserMapper;
import br.com.student.portal.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static br.com.student.portal.validation.UserValidator.validateFields;

@Service
public class AuthorizationService implements UserDetailsService {
    private UserRepository userRepository;
    private UserMapper userMapper;

    public AuthorizationService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }


}
