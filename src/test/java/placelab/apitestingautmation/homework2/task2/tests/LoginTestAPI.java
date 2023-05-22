package placelab.apitestingautmation.homework2.task2.tests;

import placelab.apitestingautmation.homework2.task2.steps.PostRequest;
import placelab.apitestingautmation.homework2.task2.utils.AuthenticationRequest;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

public class LoginTestAPI {
    private static final String host = "https://demo.placelab.com";
    private static final String path = "/api/v2/sessions";

    @Parameters({"email", "password"})
    @Test(priority = 1, groups = "positive", description = "Validate login via API with valid credentials.")
    public void testLoginWithValidCredentials(final String email, final String password) {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(email, password);

        final Response responseOnPostRequest = PostRequest.submitPostRequest(host, path, authenticationRequest);

        responseOnPostRequest.then()
                .statusCode(201)
                .contentType("application/json")
                .body("api_token", notNullValue());
    }

    @Test(priority = 2, groups = "negative", description = "Validate login via API with empty fields.")
    public void testLoginWithEmptyFields() {

        AuthenticationRequest authenticationRequest = new AuthenticationRequest(" ", " ");

        final Response responseOnPostRequest = PostRequest.submitPostRequest(host, path, authenticationRequest);

        responseOnPostRequest.then()
                .statusCode(401)
                .contentType("application/json")
                .body("error", equalTo("Password mismatch."));
    }

    @BeforeTest
    public static String generateRandomEmail() {
        String username = RandomStringUtils.randomAlphanumeric(10);
        String domain = RandomStringUtils.randomAlphanumeric(5) + ".com";
        String randomEmail = username + "@" + domain;
        return randomEmail;
    }

    @Parameters("password")
    @Test(priority = 3, groups = "negative", description = "Validate login via API with invalid email address.")
    public void testLoginWithInvalidEmail() {

        AuthenticationRequest authenticationRequest = new AuthenticationRequest(generateRandomEmail(), " ");

        final Response responseOnPostRequest = PostRequest.submitPostRequest(host, path, authenticationRequest);

        responseOnPostRequest.then()
                .statusCode(401)
                .contentType("application/json")
                .body("error", equalTo("Password mismatch."));
    }

    @BeforeMethod
    public static String generateRandomPassword() {
        String randomPassword = RandomStringUtils.randomAlphanumeric(10);
        return randomPassword;
    }

    @Parameters("email")
    @Test(priority = 4, groups = "negative", description = "Validate login via API with invalid password.")
    public void testLoginWithInvalidPassword(final String email) {

        AuthenticationRequest authenticationRequest = new AuthenticationRequest(email, generateRandomPassword());

        final Response responseOnPostRequest = PostRequest.submitPostRequest(host, path, authenticationRequest);

        responseOnPostRequest.then()
                .statusCode(401)
                .contentType("application/json")
                .body("error", equalTo("Password mismatch."));
    }
}
