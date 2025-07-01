package service;

import br.com.student.portal.dto.user.UserRequest;
import br.com.student.portal.entity.UserEntity;
import br.com.student.portal.repository.UserRepository;
import br.com.student.portal.service.UserService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    public void mustCreateUser(){
        // CRIANDO USUARIO
        var userRequest = new UserRequest();
        // ALTERANDO OS DADOS DO USUARIO
        userRequest.setEmail("1234123123@gmail.com");
        userRequest.setPassword("123@122144_1A");
        userRequest.setName("Pedrin");

        var userEntity = new UserEntity(userRequest.getName(),
                userRequest.getEmail(),
                userRequest.getPassword());

        given(userRepository.save(userEntity)).willReturn(userEntity);

        var result = userService.createUser(userRequest);

        assertEquals("1234123123@gmail.com", result.getEmail());
        assertEquals("Pedrin", result.getName());


    }
}
