package base.Steps;

import base.Constants;
import base.Objects.RegisterObjects.RegisterObject;
import base.Objects.UserObjects.ExtendedUserObject;
import base.Utils.Endpoints;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

import static base.Steps.CreateRegisterSteps.getRegisteredUserData;
import static io.restassured.RestAssured.given;

public class CreateLoginSteps {

    public static List<RegisterObject> getAllLoggedInUsers() {
        List<RegisterObject> allLoggedInUsers = new ArrayList<>();
        int currentPage = 1;
        int totalPages;

        do {
            Response response = given()
                    .queryParam(Constants.QUERY_PARAM_PAGE, currentPage)
                    .get(Endpoints.getEndpoint(Endpoints.LOGIN));

            List<RegisterObject> objects = response.jsonPath().getList(Constants.BODY_KEY_DATA, RegisterObject.class);

            allLoggedInUsers.addAll(objects);
            currentPage++;
            totalPages = response.jsonPath().getInt(Constants.RESPONSE_KEY_TOTAL_PAGES);
        } while (currentPage <= totalPages);

        return allLoggedInUsers;
    }

    public static Response getLoggedInUser(int userID) {
        return given()
                .queryParam(Constants.QUERY_PARAM_ID, userID)
                .contentType(ContentType.JSON)
                .get(Endpoints.getEndpoint(Endpoints.LOGIN));

    }

    public static Response logInExistingUser(int userID) {
        return given()
                .contentType(ContentType.JSON)
                .body(getRegisteredUserData(userID))
                .post(Endpoints.getEndpoint(Endpoints.LOGIN));
    }








}
