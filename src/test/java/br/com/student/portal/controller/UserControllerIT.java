package br.com.student.portal.controller;

import br.com.student.portal.config.IntegrationTest;
import org.junit.Test;


import static br.com.student.portal.fixture.UserFixture.getAllUsers;
import static org.apache.tomcat.websocket.Constants.FOUND;

public class UserControllerIT extends IntegrationTest {
    @Test
    public void mustGetAllUsers(){
        getAllUsers()
                .then()
                .statusCode(FOUND);
    }
}
