package base.Steps;

import base.Constants;
import base.Objects.LoginObjects.LoginObject;
import base.Objects.RegisterObjects.RegisterObject;
import base.Objects.UserObjects.ExtendedUserObject;
import base.Utils.Endpoints;
import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;

public class CreateRegisterSteps {

    private static final Log log = LogFactory.getLog(CreateRegisterSteps.class);

    public static <T> List<RegisterObject<T>> getAllRegisteredUsers(Class<T> type) {
        List<RegisterObject<T>> allRegisteredUsers = new ArrayList<>();
        int currentPage = 1;
        int totalPages;

        do {
            Response response = given()
                    .queryParam(Constants.QUERY_PARAM_PAGE, currentPage)
                    .get(Endpoints.getEndpoint(Endpoints.REGISTER));

            String responseBody = response.jsonPath().getString(Constants.BODY_KEY_DATA);
            Gson gson = new Gson();
            List<RegisterObject<T>> objects = gson.fromJson(responseBody, new TypeToken<List<RegisterObject<T>>>(){}.getType());

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

    public static LoginObject getRegisteredUserData(int userId) {
        String password = new Faker().internet().password();
        CreateRegisterSteps.postRegister(CreateUserSteps.getUserObject(userId), password);
        return new LoginObject(CreateUserSteps.getUserObject(userId).getEmail(), password);
    }

    public static ExtendedUserObject<String> getRandomRegisteredUser() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        List<ExtendedUserObject<String>> userList = CreateUserSteps.getAllUsers();

        return userList.get(random.nextInt(userList.size()));
    }

    public static RegisterObject<Integer> getLastRegisteredUser() {
        return getAllRegisteredUsers()
                .stream()
                .max(Comparator.comparingInt(RegisterObject::getId))
                .orElse(null);
    }
}
