package br.com.student.portal.service;

import br.com.student.portal.dto.user.UserRequest;
import br.com.student.portal.dto.user.UserResponse;
import br.com.student.portal.entity.UserEntity;
import br.com.student.portal.mapper.UserMapper;
import br.com.student.portal.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static br.com.student.portal.data.FixedData.superUser;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    UserMapper userMapper;
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    public void mustCreateUser() {

        //TODO: adicionar lógica da criação de objetos em uma classe isolada

        var userRequest = new UserRequest();

        var userEntity = new UserEntity(userRequest.getName(),
                userRequest.getEmail(),
                userRequest.getPassword());

        var userResponse = new UserResponse();
        userResponse.setEmail("1234123123@gmail.com");
        userResponse.setName("Pedrin");
        userResponse.setId(superUser);

        given(userMapper.userRequestIntoUserEntity(userRequest)).willReturn(userEntity);
        given(userRepository.save(userEntity)).willReturn(userEntity);
        given(userMapper.userEntityIntoUserResponse(userEntity)).willReturn(userResponse);

        var result = userService.createUser(userRequest);

        assertEquals("1234123123@gmail.com", result.getEmail());
        assertEquals("Pedrin", result.getName());
        assertEquals(superUser, result.getId());

    }

    @Test
    public void mustGetAllUsers() {
        //TODO: validar o id também, é sempre importante validar 100% as funcionalidades
        var userOne = new UserEntity("Gui",
                "gui@gmail.com", "1234");
        var userTwo = new UserEntity("Otavio",
                "otavio@gmail.com", "123456");

        var userOneResponse = new UserResponse();
        userOneResponse.setName("Gui");
        userOneResponse.setEmail("gui@gmail.com");

        var userTwoResponse = new UserResponse();
        userTwoResponse.setName("Otavio");
        userTwoResponse.setEmail("otavio@gmail.com");

        var users = List.of(userOne, userTwo);

        given(userRepository.findAll()).willReturn(users);
        given(userMapper.userEntityIntoUserResponse(userOne)).willReturn(userOneResponse);
        given(userMapper.userEntityIntoUserResponse(userTwo)).willReturn(userTwoResponse);

        var result = userService.getAllUsers();

        assertEquals("Gui", result.get(0).getName());
        assertEquals("gui@gmail.com", result.get(0).getEmail());
        assertEquals("Otavio", result.get(1).getName());
        assertEquals("otavio@gmail.com", result.get(1).getEmail());

    }

    @Test
    public void mustUpdateUser() {
        var userRequest = mock(UserRequest.class);
        var uuid = superUser;

        var userEntity = mock(UserEntity.class);
        var userResponse = mock(UserResponse.class);

        given(userRequest.getEmail()).willReturn("otavio@gmail.com");
        given(userRequest.getName()).willReturn("Otavio");
        given(userRequest.getPassword()).willReturn("Otavio1234@616");

        given(userResponse.getEmail()).willReturn("otavio@gmail.com");
        given(userResponse.getName()).willReturn("Otavio");

        given(userRepository.findById(uuid)).willReturn(Optional.of(userEntity));
        given(userMapper.userEntityIntoUserResponse(userRepository.save(userEntity))).willReturn(userResponse);

        var result = userService.updateUser(uuid, userRequest);

        assertEquals("otavio@gmail.com", result.getEmail());
        assertEquals("Otavio", result.getName());
        assertEquals(UUID.fromString("11111111-2222-3333-4444-555555555555"), superUser);
    }
}
