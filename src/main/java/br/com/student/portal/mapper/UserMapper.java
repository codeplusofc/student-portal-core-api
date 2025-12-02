package br.com.student.portal.mapper;

import br.com.student.portal.dto.user.UserRequest;
import br.com.student.portal.dto.user.UserResponse;
import br.com.student.portal.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserResponse userEntityIntoUserResponse(UserEntity userEntity) {
        return new UserResponse(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getEmail(),
                userEntity.getRole()
        );
    }

    public UserEntity userRequestIntoUserEntity(UserRequest userRequest) {
        var user = new UserEntity();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());

        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        user.setRegistration(userRequest.getRegistration());
        user.setRole("USER");
        user.setAccessEnable(true);
        return user;
    }
}