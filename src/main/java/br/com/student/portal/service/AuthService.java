package br.com.student.portal.service;

import br.com.student.portal.config.security.TokenService;
import br.com.student.portal.dto.user.UserRequest;
import br.com.student.portal.dto.user.UserResponse;
import br.com.student.portal.entity.UserEntity;
import br.com.student.portal.exception.BadRequestException;
import br.com.student.portal.mapper.UserMapper;
import br.com.student.portal.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static br.com.student.portal.validation.UserValidator.validateFields;
@AllArgsConstructor
@Service
public class AuthService {

    private AuthenticationManager authenticationManager;
    private TokenService tokenService;
    private UserRepository userRepository;
    private UserMapper userMapper;

    public UserResponse createUser(UserRequest userRequest) {
        var userEntity = userMapper.userRequestIntoUserEntity(userRequest);

        validateFields(userEntity);

        if (userRepository.findByEmail(userRequest.getEmail()) != null) {
            throw new BadRequestException("There's another user with this email");
        }

        var encryptedPassword = new BCryptPasswordEncoder().encode(userEntity.getPassword());

        var user = new UserEntity(userEntity.getName()
                , userEntity.getEmail()
                , encryptedPassword
                , userEntity.getRole());

        return userMapper.userEntityIntoUserResponse(userRepository.save(user));
    }
    public String loginUser(UserRequest user){
        var usernamePassword = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        return tokenService.generateToken((UserEntity)auth.getPrincipal());

    }

}
