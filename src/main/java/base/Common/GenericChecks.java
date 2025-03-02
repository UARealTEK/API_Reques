package base.Common;

import io.restassured.response.Response;

public class GenericChecks {

    public static boolean isRequestValid(Response response) {
        return response.then().extract().statusCode() == 200;
    }
    public static boolean isElementNotFound(Response response) {
        return response.then().extract().statusCode() == 404;
    }
}
