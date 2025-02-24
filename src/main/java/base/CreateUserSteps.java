package base;

import base.utils.Endpoints;
import io.restassured.response.Response;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class CreateUserSteps {

    public static CreateUserObject postNewUserWithResponse(CreateUserObject object) throws IOException {

        return given()
                .body(object)
                .post(Endpoints.getEndpoint(Endpoints.USERS))
                .then()
                .extract()
                .body()
                .jsonPath().getObject("", CreateUserObject.class);
    }

    public static void postNewUser(CreateUserObject object) {
        given()
                .body(object)
                .post(Endpoints.getEndpoint(Endpoints.USERS));
    }


}
