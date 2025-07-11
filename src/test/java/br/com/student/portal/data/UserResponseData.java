package br.com.student.portal.data;

import br.com.student.portal.dto.user.UserResponse;

import static br.com.student.portal.data.FixedData.superUser;

public class UserResponseData {
    public static UserResponse generateUserResponse(){
        var userResponse = new UserResponse();
        userResponse.setId(superUser);
        userResponse.setName("Markin");
        userResponse.setEmail("otaviocolela123@gmail.com");
        return userResponse;
    }
}
