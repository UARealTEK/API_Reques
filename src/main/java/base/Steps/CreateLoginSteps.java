package base.Steps;

import base.Constants;
import base.Objects.LoginObjects.LoginObject;
import base.Utils.Endpoints;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class CreateLoginSteps {

    public static List<LoginObject> getAllLoggedInUsers() {
        List<LoginObject> allLoggedInUsers = new ArrayList<>();
        int currentPage = 1;
        int totalPages;

        do {
            Response response = given()
                    .queryParam(Constants.QUERY_PARAM_PAGE, currentPage)
                    .get(Endpoints.getEndpoint(Endpoints.LOGIN));
            List<LoginObject> objects = response.jsonPath().getList(Constants.BODY_KEY_DATA, LoginObject.class);

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




}
