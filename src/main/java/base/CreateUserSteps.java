package base;

import base.Objects.UserObject;
import base.Utils.Endpoints;
import base.Utils.ParserHelper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

import static io.restassured.RestAssured.given;

public class CreateUserSteps {

    private static final Log log = LogFactory.getLog(CreateUserSteps.class);

    public static Response postNewUserWithResponse(UserObject user) {
        return  given()
                .contentType(ContentType.JSON)
                .body(user)
                .post(Endpoints.getEndpoint(Endpoints.USERS));
    }

    public static List<UserObject> getAllUsers() {
        Response response = given()
                .get(Endpoints.getEndpoint(Endpoints.USERS));

        return response.jsonPath().getList("data", UserObject.class);
    }

    public static Response getAllUsersRequest() {
        return given()
                .get(Endpoints.getEndpoint(Endpoints.USERS));
    }

    public static Response getUser(UserObject user) {
        return given()
                .queryParam("id", user.getId())
                .get(Endpoints.getEndpoint(Endpoints.USERS));
    }

}
