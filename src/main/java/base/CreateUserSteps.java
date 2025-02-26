package base;

import base.utils.Endpoints;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class CreateUserSteps {

    public static JSONObject postNewUserWithResponse(JSONObject object) throws IOException {

        String response = given()
                .body(object)
                .post(Endpoints.getEndpoint(Endpoints.USERS))
                .then()
                .extract()
                .body()
                .asString();
        return new JSONObject(response);
    }

    public static void postNewUser(JSONObject object) {
        given()
                .body(object)
                .post(Endpoints.getEndpoint(Endpoints.USERS));
    }


}
