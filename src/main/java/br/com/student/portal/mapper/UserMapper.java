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

    private final PasswordEncoder passwordEncoder;  // ✅ INJETAR PasswordEncoder

    public UserResponse userEntityIntoUserResponse(UserEntity userEntity) {
        return new UserResponse(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getEmail(),
                userEntity.getRole()
        );
    }

    public UserEntity userRequestIntoUserEntity(UserRequest userRequest) {
        UserEntity user = new UserEntity();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());

        // ✅ CRITICAL FIX: Sempre criptografar a senha!
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        user.setRegistration(userRequest.getRegistration());
        user.setRole("USER");  // Define role padrão
        user.setAccessEnable(true);  // Habilitar acesso por padrão
        return user;
    }
}