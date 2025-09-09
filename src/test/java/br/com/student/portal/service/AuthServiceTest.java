package br.com.student.portal.service;

import br.com.student.portal.dto.user.UserRequest;
import br.com.student.portal.dto.user.UserResponse;
import br.com.student.portal.entity.UserEntity;
import br.com.student.portal.mapper.UserMapper;
import br.com.student.portal.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static br.com.student.portal.data.FixedData.superUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;


@RunWith(MockitoJUnitRunner.class)
public class AuthServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    UserRequest userRequest = new UserRequest();
    @Mock
    UserEntity userEntity = new UserEntity();
    @Mock
    UserResponse userResponse = new UserResponse();
    @InjectMocks
    private AuthService authService;

    @Before
    public void setup() {
        userRequest = new UserRequest("Markin",
                "otaviocolela123@gmail.com", "1234@OTAVIO!");
        userEntity =  new UserEntity("Pedrin", "Pedrin@gmail.com", "Pedrin1243124@", "USER");
        userResponse = ---------------------------------------------new UserResponse(superUser, "Markin", "otaviocolela123@gmail.com" );
    }

    @Test
    public void mustCreateUser(){
        given(userMapper.userRequestIntoUserEntity(userRequest)).willReturn(userEntity);
        given(userMapper.userEntityIntoUserResponse(userRepository.save(userEntity))).willReturn(userResponse);

        var result = authService.createUser(userRequest);

        assertEquals("otaviocolela123@gmail.com",result.getEmail());
        assertEquals("Markin", result.getName());

    }

}
