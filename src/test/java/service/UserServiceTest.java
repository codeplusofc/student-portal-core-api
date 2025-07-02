package service;

import br.com.student.portal.dto.user.UserRequest;
import br.com.student.portal.dto.user.UserResponse;
import br.com.student.portal.entity.UserEntity;
import br.com.student.portal.mapper.UserMapper;
import br.com.student.portal.repository.UserRepository;
import br.com.student.portal.service.UserService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    UserMapper userMapper;
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    public void mustCreateUser(){
        var uuid = UUID.randomUUID();
        // CRIANDO USUARIO
        var userRequest = new UserRequest();
        // ALTERANDO OS DADOS DO USUARIO
        userRequest.setEmail("1234123123@gmail.com");
        userRequest.setPassword("123@122144_1A");
        userRequest.setName("Pedrin");

        var userEntity = new UserEntity(userRequest.getName(),
                userRequest.getEmail(),
                userRequest.getPassword());

        var userResponse = new UserResponse();
        userResponse.setEmail("1234123123@gmail.com");
        userResponse.setName("Pedrin");
        userResponse.setId(uuid);

        given(userMapper.userRequestIntoUserEntity(userRequest)).willReturn(userEntity);
        given(userRepository.save(userEntity)).willReturn(userEntity);
        given(userMapper.userEntityIntoUserResponse(userEntity)).willReturn(userResponse);


        var result = userService.createUser(userRequest);

        assertEquals("1234123123@gmail.com", result.getEmail());
        assertEquals("Pedrin", result.getName());
        assertEquals(uuid,uuid);

    }
}
