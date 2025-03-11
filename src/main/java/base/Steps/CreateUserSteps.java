package base.Steps;

import base.Constants;
import base.Objects.UserObjects.BaseUserObject;
import base.Objects.UserObjects.ExtendedUserObject;
import base.Objects.UserObjects.ExtendedUserObjectFactory;
import base.Utils.Endpoints;
import base.Utils.FakerData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;

public class CreateUserSteps {

    public static Response postNewUserWithResponse(BaseUserObject user) {
        return  given()
                .contentType(ContentType.JSON)
                .body(user)
                .post(Endpoints.getEndpoint(Endpoints.USERS));
    }

    public static <T extends Comparable<T>> List<ExtendedUserObject<T>> getAllUsers() {
        List<ExtendedUserObject<T>> allUsers = new ArrayList<>();
        int currentPage = 1;
        int totalPages;

        do {
            Response response = given()
                    .queryParam(Constants.QUERY_PARAM_PAGE,currentPage)
                    .get(Endpoints.getEndpoint(Endpoints.USERS));

            String responseBody = response.jsonPath().getString(Constants.BODY_KEY_DATA);
            Gson gson = new Gson();
            List<ExtendedUserObject<T>> users = gson.fromJson(responseBody, new TypeToken<List<ExtendedUserObject<T>>>(){}.getType());

            allUsers.addAll(users);
            totalPages = response.jsonPath().getInt(Constants.RESPONSE_KEY_TOTAL_PAGES);
            currentPage++;

        } while (currentPage <= totalPages);

        return allUsers;
    }

    public static Response getAllUsersRequest() {
        return given()
                .get(Endpoints.getEndpoint(Endpoints.USERS));
    }

//    public static int getLastCreatedUser() {
//        getAllUsers().stream().max(Comparator.comparingInt(ExtendedUserObject::getId));
//    }

    public static Response getUser(int id) {
        return given()
                .queryParam(Constants.QUERY_PARAM_ID, id)
                .get(Endpoints.getEndpoint(Endpoints.USERS));
    }

//    public static ExtendedUserObject<Integer> getUserObject(int userID) {
//        return getAllUsers().stream()
//                .filter(user -> Integer.parseInt(user.getId()) == userID)
//                .findFirst().orElse(null);
//    }

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
