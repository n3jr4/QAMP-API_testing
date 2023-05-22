package placelab.apitestingautmation.homework2.task2.steps;

import placelab.apitestingautmation.homework2.task2.utils.AuthenticationRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import java.util.logging.Logger;

import io.restassured.response.Response;

public class PostRequest {
    public static Response submitPostRequest(final String host, final String path, AuthenticationRequest authenticationRequest) {
        final Logger LOGGER = Logger.getLogger("Login Test");
        LOGGER.info("Submit authentication POST request");

        return RestAssured.given()
                .baseUri(host)
                .contentType(ContentType.JSON)
                .body(authenticationRequest.requestBody.toString())
                .when()
                .post(path);
    }
}


