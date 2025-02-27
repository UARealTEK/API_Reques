package base.Common;

import base.Constants;
import base.CreateUserSteps;
import base.Utils.Threshold;
import io.restassured.response.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Checks {

    private static final Log log = LogFactory.getLog(Checks.class);

    public static boolean isCreatedAtEqual(Response response) {
        JSONObject receivedObjectBody = new JSONObject(response.then().extract().body().asString());

        ZonedDateTime currentTime = ZonedDateTime.now(ZoneOffset.UTC)
                .truncatedTo(ChronoUnit.SECONDS);
        ZonedDateTime createdTime = ZonedDateTime.parse(receivedObjectBody.getString("createdAt"), DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)
                        .withZone(ZoneOffset.UTC))
                .truncatedTo(ChronoUnit.SECONDS);
        return Threshold.isEqual(currentTime.toEpochSecond(),createdTime.toEpochSecond());
    }

    public static boolean isUserCreated(Response response) {
        return response.then().extract().statusCode() == 201;
    }

    public static boolean isGetRequestValid(Response response) {
        return response.then().extract().statusCode() == 200;
    }
}
