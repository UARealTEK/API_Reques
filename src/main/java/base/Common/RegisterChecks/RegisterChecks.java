package base.Common.RegisterChecks;

import base.Objects.UserObjects.ExtendedUserObject;

import io.restassured.response.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class RegisterChecks {

    private static final Log log = LogFactory.getLog(RegisterChecks.class);

    public static <T extends Number & Comparable<T>> boolean isIDMatchedWithResponseID(Response responseBody, ExtendedUserObject<Integer> passedInBody) {
        return responseBody.then().extract().body().jsonPath().getInt("id") == passedInBody.getId();
    }
}
