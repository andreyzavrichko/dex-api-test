package com.dex;


import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

@DisplayName("Авторизация")
public class LoginTest {

    private final static String IDENTITY_URL = "https://api-dev.k3s.dex-it.ru/identity/";

    @Test
    @Feature("Авторизация")
    @Story("Вход в систему")
    @DisplayName("Получение токена - позитивный сценарий")
    void loginPositiveTest(){
        Response response = given()
                .contentType(ContentType.URLENC)
                .baseUri(IDENTITY_URL)
                .log().all()
                .when()
                .formParam("client_id","admin.client")
                .formParam("client_secret","9F45EA47-9BD6-48D8-B218-273A256DB093")
                .formParam("grant_type","password")
                .formParam("scope","openid profile offline_access admin-api policy")
                .formParam("username","test@gmail.com")
                .formParam("password","005")
                .post("connect/token")
                .then()
                .statusCode(200)
                .contentType(JSON)
                .body("token_type", is("Bearer"))
                .log().all()
                .extract().response();
    }
}
