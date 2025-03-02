package base.Common.UserChecks;

import base.Constants;
import base.Utils.Threshold;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class UserChecks {

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

    public static boolean isUserNotFound(Response response) {
        return response.then().extract().statusCode() == 404;
    }
}
