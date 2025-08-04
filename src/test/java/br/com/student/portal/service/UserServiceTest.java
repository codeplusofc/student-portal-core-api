package br.com.student.portal.service;

import br.com.student.portal.dto.user.UserRequest;
import br.com.student.portal.dto.user.UserResponse;
import br.com.student.portal.entity.UserEntity;
import br.com.student.portal.exception.ObjectNotFoundException;
import br.com.student.portal.mapper.UserMapper;
import br.com.student.portal.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static br.com.student.portal.data.FixedData.superUser;
import static br.com.student.portal.data.UserEntityData.generateUserEntity;
import static br.com.student.portal.data.UserRequestData.generateUserRequest;
import static br.com.student.portal.data.UserResponseData.generateUserResponse;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    UserMapper userMapper;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserService userService;

    @Mock
    UserRequest userRequest;
    @Mock
    UserEntity userEntity;
    @Mock
    UserResponse userResponse;

    @BeforeEach
    public void setup() {
        userRequest = new UserRequest("Markin",
                "otaviocolela123@gmail.com", "1234@OTAVIO!");
        userEntity =  new UserEntity("Pedrin", "Pedrin@gmail.com", "Pedrin1243124@");
        userResponse = new UserResponse(superUser, "Markin", "otaviocolela123@gmail.com" );
    }

    @Test
    public void mustCreateUser() {
        given(userMapper.userRequestIntoUserEntity(userRequest)).willReturn(userEntity);
        given(userRepository.save(userEntity)).willReturn(userEntity);
        given(userMapper.userEntityIntoUserResponse(userEntity)).willReturn(userResponse);

        var result = userService.createUser(userRequest);

        assertEquals("otaviocolela123@gmail.com", result.getEmail());
        assertEquals("Markin", result.getName());
        assertEquals(superUser, result.getId());

    }

    @Test
    public void mustGetAllUsers() {
        var userOne = generateUserEntity();
        var userTwo = generateUserEntity();
        var userOneResponse = generateUserResponse();
        var userTwoResponse = generateUserResponse();
        var users = List.of(userOne, userTwo);

        given(userRepository.findAll()).willReturn(users);
        given(userMapper.userEntityIntoUserResponse(userOne)).willReturn(userOneResponse);
        given(userMapper.userEntityIntoUserResponse(userTwo)).willReturn(userTwoResponse);

        var result = userService.getAllUsers();

        assertEquals("Markin", result.get(0).getName());
        assertEquals("otaviocolela123@gmail.com", result.get(0).getEmail());
        assertEquals("Markin", result.get(1).getName());
        assertEquals("otaviocolela123@gmail.com", result.get(1).getEmail());

    }

    @Test
    public void mustNotFindUsers(){
        thenThrownBy(() -> userService.getAllUsers())
                .isInstanceOf(ObjectNotFoundException.class);

    }

    @Test
    public void mustUpdateUser() {
        var uuid = superUser;
        var userEntity = generateUserEntity();
        var userResponse = generateUserResponse();

        given(userRepository.findById(uuid)).willReturn(Optional.of(userEntity));
        given(userMapper.userEntityIntoUserResponse(userRepository.save(userEntity))).willReturn(userResponse);

        var result = userService.updateUser(uuid, userRequest);

        assertEquals("otaviocolela123@gmail.com", result.getEmail());
        assertEquals("Markin", result.getName());
        assertEquals(UUID.fromString("11111111-2222-3333-4444-555555555555"), superUser);
    }

    @Test
    public void mustDeleteUser(){
        given(userRepository.findById(superUser)).willReturn(Optional.of(userEntity));
        userService.deleteUser(superUser);
        verify(userRepository, times(1)).delete(userEntity);
    }
}
