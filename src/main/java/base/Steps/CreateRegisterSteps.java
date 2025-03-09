package base.Steps;

import base.Constants;
import base.Objects.LoginObjects.LoginObject;
import base.Objects.RegisterObjects.RegisterObject;
import base.Objects.UserObjects.ExtendedUserObject;
import base.Utils.Endpoints;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;

public class CreateRegisterSteps {

    public static List<RegisterObject> getAllRegisteredUsers() {
        List<RegisterObject> allRegisteredUsers = new ArrayList<>();
        int currentPage = 1;
        int totalPages;

        do {
            Response response = given()
                    .queryParam(Constants.QUERY_PARAM_PAGE, currentPage)
                    .get(Endpoints.getEndpoint(Endpoints.REGISTER));
            List<RegisterObject> objects = response.jsonPath().getList(Constants.BODY_KEY_DATA, RegisterObject.class);

            allRegisteredUsers.addAll(objects);
            currentPage++;
            totalPages = response.jsonPath().getInt(Constants.RESPONSE_KEY_TOTAL_PAGES);
        } while (currentPage <= totalPages);

        return allRegisteredUsers;
    }

    public static Response getAllRegisteredUsersRequest() {
        return given()
                .get(Endpoints.getEndpoint(Endpoints.REGISTER));
    }

    public static Response getRegisteredUser(int userID) {
        return given()
                .queryParam(Constants.QUERY_PARAM_ID, userID)
                .get(Endpoints.getEndpoint(Endpoints.REGISTER));
    }

    public static Response postRegister(ExtendedUserObject body) {
        RegisterObject object = new RegisterObject();
        object.setEmail(body.getEmail());
        object.setPassword(new Faker().internet().password());
        return given()
                .contentType(ContentType.JSON)
                .body(object)
                .post(Endpoints.getEndpoint(Endpoints.REGISTER));
    }

    public static void postRegister(ExtendedUserObject body, String password) {
        RegisterObject object = new RegisterObject();
        object.setEmail(body.getEmail());
        object.setPassword(password);
        given()
                .contentType(ContentType.JSON)
                .body(object)
                .post(Endpoints.getEndpoint(Endpoints.REGISTER));
    }

    public static LoginObject getRegisteredUserData(int userId) {
        String password = new Faker().internet().password();
        CreateRegisterSteps.postRegister(CreateUserSteps.getUserObject(userId), password);
        return new LoginObject(CreateUserSteps.getUserObject(userId).getEmail(), password);
    }

    public static ExtendedUserObject getRandomRegisteredUser() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        List<ExtendedUserObject> userList = CreateUserSteps.getAllUsers();

        return userList.get(random.nextInt(userList.size()));
    }

    public static RegisterObject getLastRegisteredUser() {
        return getAllRegisteredUsers()
                .stream()
                .max(Comparator.comparingInt(RegisterObject::getId))
                .orElse(null);
    }
}
