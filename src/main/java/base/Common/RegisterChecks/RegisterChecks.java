package base.Common.RegisterChecks;

import base.Objects.UserObjects.ExtendedUserObject;

import io.restassured.response.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class RegisterChecks {

    private static final Log log = LogFactory.getLog(RegisterChecks.class);

    public static boolean isIDMatchedWithResponseID(Response responseBody, ExtendedUserObject passedInBody) {
        return responseBody.then().extract().body().jsonPath().getInt("id") == Integer.parseInt(passedInBody.getId());
    }
}
