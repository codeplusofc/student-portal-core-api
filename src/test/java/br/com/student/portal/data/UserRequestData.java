package br.com.student.portal.data;

import br.com.student.portal.dto.user.UserRequest;

public class UserRequestData {
    public static UserRequest generateUserRequest(){
        var userRequest = new UserRequest();
        userRequest.setName("Pedrin");
        userRequest.setPassword("Pedrin123@");
        userRequest.setEmail("Pedrin@gmail.com");
        return userRequest;
    }
}
