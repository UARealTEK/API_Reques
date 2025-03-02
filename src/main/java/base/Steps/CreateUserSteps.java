package base.Steps;

import base.Constants;
import base.Objects.UserObjects.BaseUserObject;
import base.Objects.UserObjects.ExtendedUserObject;
import base.Utils.Endpoints;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static io.restassured.RestAssured.given;

public class CreateUserSteps {

    public static Response postNewUserWithResponse(BaseUserObject user) {
        return  given()
                .contentType(ContentType.JSON)
                .body(user)
                .post(Endpoints.getEndpoint(Endpoints.USERS));
    }

    public static List<ExtendedUserObject> getAllUsers() {
        List<ExtendedUserObject> allUsers = new ArrayList<>();
        int currentPage = 1;
        int totalPages;

        do {
            Response response = given()
                    .queryParam("page",currentPage)
                    .get(Endpoints.getEndpoint(Endpoints.USERS));

            List<ExtendedUserObject> users = response.jsonPath().getList(Constants.BODY_KEY_DATA, ExtendedUserObject.class);
            allUsers.addAll(users);
            totalPages = response.jsonPath().getInt("total_pages");
            currentPage++;

        } while (currentPage <= totalPages);

        return allUsers;
    }

    public static Response getAllUsersRequest() {
        return given()
                .get(Endpoints.getEndpoint(Endpoints.USERS));
    }

    public static ExtendedUserObject getLastCreatedUser() {
        return getAllUsers().stream()
                 .max(Comparator.comparingInt(user -> Integer.parseInt(user.getId()))).orElse(null);
    }

    public static Response getUser(int id) {
        return given()
                .queryParam(Constants.QUERY_PARAM_ID, id)
                .get(Endpoints.getEndpoint(Endpoints.USERS));
    }

}
