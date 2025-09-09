package br.com.student.portal.mapper;

import br.com.student.portal.dto.user.UserRequest;
import br.com.student.portal.dto.user.UserResponse;
import br.com.student.portal.entity.UserEntity;
import org.springframework.stereotype.Component;



@Component
public class UserMapper {


    public UserResponse userEntityIntoUserResponse(UserEntity userEntity){
        return new UserResponse(userEntity.getId(),
                userEntity.getName(),
                userEntity.getEmail(),
                userEntity.getRole());
    }

    public UserEntity userRequestIntoUserEntity(UserRequest userRequest){
        return new UserEntity(userRequest.getName(),
                userRequest.getEmail(),
                userRequest.getPassword(), "USER");
    }
}

