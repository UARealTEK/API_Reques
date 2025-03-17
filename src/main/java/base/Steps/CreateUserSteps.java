package base.Steps;

import base.Common.Constants.ConstantKeys;
import base.Objects.UserObjects.BaseUserObject;
import base.Objects.UserObjects.ExtendedUserObject;
import base.Utils.Endpoints;
import base.Utils.FakerData;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.*;

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
                    .queryParam(ConstantKeys.QUERY_PARAM_PAGE, currentPage)
                    .get(Endpoints.getEndpoint(Endpoints.USERS));

            List<ExtendedUserObject> users = response.jsonPath().getList(ConstantKeys.BODY_KEY_DATA, ExtendedUserObject.class);

            allUsers.addAll(users);
            totalPages = response.jsonPath().getInt(ConstantKeys.RESPONSE_KEY_TOTAL_PAGES);
            currentPage++;

        } while (currentPage <= totalPages);

        return allUsers;
    }

    public static Response getAllUsersRequest() {
        return given()
                .get(Endpoints.getEndpoint(Endpoints.USERS));
    }

    public static ExtendedUserObject getLastCreatedUser() {
        List<ExtendedUserObject> allUsers = getAllUsers();
        return allUsers.stream()
                .max(Comparator.comparingInt(user -> Integer.parseInt(user.getId()))).orElse(new ExtendedUserObject());
    }

    public static Response getUser(int id) {
        return given()
                .queryParam(ConstantKeys.QUERY_PARAM_ID, id)
                .get(Endpoints.getEndpoint(Endpoints.USERS));
    }

    public static ExtendedUserObject getUserObject(Integer userID) {
        List<ExtendedUserObject> allUsers = getAllUsers();
        return allUsers.stream()
                .filter(user -> Integer.parseInt(user.getId()) == userID)
                .findFirst().orElse(null);
    }

    public static Response putUser(ExtendedUserObject user) {
        BaseUserObject objectBody = FakerData.createFakerUser();
        return
                given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .body(objectBody)
                        .put(Endpoints.getEndpoint(Endpoints.USERS) + "/" + user.getId());
    }

    public static Response deleteUser(ExtendedUserObject user) {
       return given()
                .delete(Endpoints.getEndpoint(Endpoints.USERS) + "/" + user.getId());
    }

}
