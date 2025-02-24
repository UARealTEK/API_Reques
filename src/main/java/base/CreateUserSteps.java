package base;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CreateUserSteps {

    public static CreateUserObject postNewUserWithResponse(CreateUserObject object) {

        String body = given()
                .body(object)
                .post()
                .then()
                .extract()
                .body()
                .jsonPath().get();

        return CreateUserObject.getObjectFromJson(body);
    }

    public static void postNewUser(CreateUserObject object) {
        given()
                .body(object)
                .post();
    }


}
