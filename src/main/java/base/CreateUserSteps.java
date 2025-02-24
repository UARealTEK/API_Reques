package base;

import io.restassured.response.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class CreateUserSteps {

    public static CreateUserObject postNewUserWithResponse(CreateUserObject object) throws IOException {

        return given()
                .body(object)
                .post()
                .then()
                .extract()
                .body()
                .jsonPath().getObject(new String(Files.readAllBytes(Paths.get(Constants.JSON_FILE_PATH))), CreateUserObject.class);
    }

    public static void postNewUser(CreateUserObject object) {
        given()
                .body(object)
                .post();
    }


}
