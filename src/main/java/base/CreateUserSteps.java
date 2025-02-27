package base;

import base.Objects.BaseUserObject;
import base.Objects.ExtendedUserObject;
import base.Utils.Endpoints;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

import static io.restassured.RestAssured.given;

public class CreateUserSteps {


    private static final Log log = LogFactory.getLog(CreateUserSteps.class);

    public static Response postNewUserWithResponse(BaseUserObject user) {
        return  given()
                .contentType(ContentType.JSON)
                .body(user)
                .post(Endpoints.getEndpoint(Endpoints.USERS));
    }

    public static List<ExtendedUserObject> getAllUsers() {
        Response response = given()
                .get(Endpoints.getEndpoint(Endpoints.USERS));

        return response.jsonPath().getList(Constants.BODY_KEY_DATA, ExtendedUserObject.class);
    }

    public static Response getAllUsersRequest() {
        return given()
                .get(Endpoints.getEndpoint(Endpoints.USERS));
    }

    public static Response getUser(ExtendedUserObject user) {
        return given()
                .queryParam(Constants.QUERY_PARAM_ID, user.getId())
                .get(Endpoints.getEndpoint(Endpoints.USERS));
    }

}
