package br.com.student.portal.service;

import br.com.student.portal.controller.AuthController;
import br.com.student.portal.dto.user.UserRequest;
import br.com.student.portal.dto.user.UserResponse;
import br.com.student.portal.entity.UserEntity;
import br.com.student.portal.exception.ObjectNotFoundException;
import br.com.student.portal.mapper.UserMapper;
import br.com.student.portal.repository.UserRepository;
import br.com.student.portal.security.AuthorizationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static br.com.student.portal.data.FixedData.superUser;
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
    @Mock
    UserRequest userRequest;
    @Mock
    UserEntity userEntity;
    @Mock
    UserResponse userResponse;
    @InjectMocks
    UserService userService;

    @Before
    public void setup() {
        userRequest = new UserRequest("Markin",
                "otaviocolela123@gmail.com", "1234@OTAVIO!");
        userEntity =  new UserEntity("Pedrin", "Pedrin@gmail.com", "Pedrin1243124@", "USER");
        userResponse = new UserResponse(superUser, "Markin", "otaviocolela123@gmail.com" );
    }
    
    @Test
    public void mustGetAllUsers() {
        var users = List.of(userEntity, userEntity);

        given(userRepository.findAll()).willReturn(users);
        given(userMapper.userEntityIntoUserResponse(userEntity)).willReturn(userResponse);
        given(userMapper.userEntityIntoUserResponse(userEntity)).willReturn(userResponse);

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
