package br.com.student.portal.data;

import br.com.student.portal.entity.UserEntity;

public class UserEntityData {
    public static UserEntity generateUserEntity(){
        var userEntity = new UserEntity("Pedrin", "Pedrin@gmail.com", "Pedrin1243124@");
        return userEntity;
    }
}
