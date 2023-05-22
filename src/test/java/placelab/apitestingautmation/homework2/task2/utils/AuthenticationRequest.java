package placelab.apitestingautmation.homework2.task2.utils;

import org.json.JSONObject;

public class AuthenticationRequest {
    private static String email;
    private static String password;
    public JSONObject requestBody;

    public AuthenticationRequest(final String email, final String password) {
        this.email = email;
        this.password = password;
        createMainBody();
    }

    private void createMainBody() {
        JSONObject data = new JSONObject();
        data.put("email", email);
        data.put("password", password);
        requestBody = data;
    }
}
