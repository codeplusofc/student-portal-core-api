package br.com.student.portal.mapper;

import br.com.student.portal.dto.user.UserRequest;
import br.com.student.portal.dto.user.UserResponse;
import br.com.student.portal.dto.vote.VoteResponse;
import br.com.student.portal.entity.UserEntity;
import br.com.student.portal.exception.ObjectNotFoundException;
import org.springframework.stereotype.Component;



@Component
public class UserMapper {


    public UserResponse userEntityIntoUserResponse(UserEntity userEntity){
        return new UserResponse(userEntity.getId(),
                userEntity.getName(),
                userEntity.getEmail());
    }

    public UserEntity userRequestIntoUserEntity(UserRequest userRequest){
        return new UserEntity(userRequest.getName(),
                userRequest.getPassword(),
                userRequest.getEmail(), "USER");
    }
}

