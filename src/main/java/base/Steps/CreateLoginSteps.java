package base.Steps;

import base.Common.Constants.ConstantKeys;
import base.Objects.RegisterObjects.RegisterObject;
import base.Utils.Endpoints;
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
                    .queryParam(ConstantKeys.QUERY_PARAM_PAGE, currentPage)
                    .get(Endpoints.getEndpoint(Endpoints.LOGIN));

            List<RegisterObject> objects = response.jsonPath().getList(ConstantKeys.BODY_KEY_DATA, RegisterObject.class);

            allLoggedInUsers.addAll(objects);
            currentPage++;
            totalPages = response.jsonPath().getInt(ConstantKeys.RESPONSE_KEY_TOTAL_PAGES);
        } while (currentPage <= totalPages);

        return allLoggedInUsers;
    }

    public static Response getLoggedInUser(int userID) {
        return given()
                .queryParam(ConstantKeys.QUERY_PARAM_ID, userID)
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
