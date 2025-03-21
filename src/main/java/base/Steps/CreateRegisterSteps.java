package base.Steps;

import base.Common.Constants.ConstantKeys;
import base.Objects.LoginObjects.LoginObject;
import base.Objects.RegisterObjects.RegisterObject;
import base.Objects.UserObjects.ExtendedUserObject;
import base.Utils.Endpoints;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.ArrayList;
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
                    .queryParam(ConstantKeys.QUERY_PARAM_PAGE, currentPage)
                    .get(Endpoints.getEndpoint(Endpoints.REGISTER));

            List<RegisterObject> objects = response.jsonPath().getList(ConstantKeys.BODY_KEY_DATA, RegisterObject.class);

            allRegisteredUsers.addAll(objects);
            currentPage++;
            totalPages = response.jsonPath().getInt(ConstantKeys.RESPONSE_KEY_TOTAL_PAGES);
        } while (currentPage <= totalPages);

        return allRegisteredUsers;
    }


    public static Response getAllRegisteredUsersRequest() {
        return given()
                .get(Endpoints.getEndpoint(Endpoints.REGISTER));
    }

    public static Response getRegisteredUser(int userID) {
        return given()
                .queryParam(ConstantKeys.QUERY_PARAM_ID, userID)
                .get(Endpoints.getEndpoint(Endpoints.REGISTER));
    }

    public static Response postRegister(ExtendedUserObject body) {
        JSONObject object = new JSONObject();
        object.put("email",body.getEmail());
        object.put("password",new Faker().internet().password());

        return given()
                .contentType(ContentType.JSON)
                .body(object.toString())
                .post(Endpoints.getEndpoint(Endpoints.REGISTER));
    }

    public static void postRegister(ExtendedUserObject body, String password) {
        JSONObject object = new JSONObject();
        object.put("email",body.getEmail());
        object.put("password",password);
        given()
                .contentType(ContentType.JSON)
                .body(object)
                .post(Endpoints.getEndpoint(Endpoints.REGISTER));
    }

    public static LoginObject getRegisteredUserData(Integer userId) {
        String password = new Faker().internet().password();
        ExtendedUserObject object = CreateUserSteps.getUserObject(userId);
        CreateRegisterSteps.postRegister(object, password);
        return new LoginObject(CreateUserSteps.getUserObject(userId).getEmail(), password);
    }

    public static ExtendedUserObject getRandomRegisteredUser() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        List<ExtendedUserObject> userList = CreateUserSteps.getAllUsers();

        return userList.get(random.nextInt(userList.size()));
    }
}
