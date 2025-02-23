package base;

import static io.restassured.RestAssured.given;

public class CreateUserSteps {

    public void postNewUser(CreateUserObject object) {
        given()
                .body(object)
                .post();
    }

    public void postNewUserWithResponse(CreateUserObject object) {
        given()
                .body(object)
                .post();
    }


}
