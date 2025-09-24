package br.com.student.portal.fixture;

;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;




public class UserFixture {
    public static Response getAllUsers(){
        return given()
                .basePath("/api/users")
                .when()
                .get();

    }
}
