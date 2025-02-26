package base;

import base.utils.Endpoints;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class CreateUserSteps {

    public static Response postNewUserWithResponse(JSONObject object) {

        return  given()
                .body(object)
                .post(Endpoints.getEndpoint(Endpoints.USERS));
    }

    public static Response getUser(String idKey) {
        return given()
                .queryParam("id", idKey)
                .get(Endpoints.getEndpoint(Endpoints.USERS));
    }


}
