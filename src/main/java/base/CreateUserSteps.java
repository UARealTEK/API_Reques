package base;

public class CreateUserSteps {

    public void postNewUser(CreateUserObject object) {
        given()
                .body(CreateUserObject, object)
                .post();
    }

    public void postNewUserWithResponse(CreateUserObject object) {
        given()
                .body(CreateUserObject, object)
                .post();
    }


}
