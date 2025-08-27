package br.com.student.portal.controller;

import br.com.student.portal.dto.user.UserRequest;
import br.com.student.portal.dto.user.UserResponse;
import br.com.student.portal.entity.UserEntity;
import br.com.student.portal.exception.BadRequestException;
import br.com.student.portal.mapper.UserMapper;
import br.com.student.portal.repository.UserRepository;
import br.com.student.portal.security.TokenService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static br.com.student.portal.validation.UserValidator.validateFields;

@RestController
@RequestMapping("api/users")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid UserRequest user){
        var usernamePassword = new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((UserEntity)auth.getPrincipal());

        return ResponseEntity.ok().build();
    }

    public UserResponse createUser(UserRequest userRequest) {
        var userEntity = userMapper.userRequestIntoUserEntity(userRequest);
        validateFields(userEntity);
        if(userRepository.findByEmail(userRequest.getEmail()) != null){
            throw new BadRequestException("There's another user with this email");
        }
        var encryptedPassword = new BCryptPasswordEncoder().encode(userEntity.getPassword());
        var user = new UserEntity(userEntity.getName()
                ,userEntity.getEmail()
                ,encryptedPassword
                ,userEntity.getRole());


        return userMapper.userEntityIntoUserResponse(userRepository.save(user));

    }
}
