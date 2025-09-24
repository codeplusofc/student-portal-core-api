package br.com.student.portal.utils;

import io.restassured.RestAssured;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RequestSpec {

    public static void on(int port) {
        RestAssured.port = port;
    }
}