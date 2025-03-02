package base.Common;

import io.restassured.response.Response;

public class GenericChecks {

    public static boolean isGetRequestValid(Response response) {
        return response.then().extract().statusCode() == 200;
    }

}
