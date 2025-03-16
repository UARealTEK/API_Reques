package base.Common.UserChecks;

import base.Constants;
import base.Objects.UserObjects.ExtendedUserObject;
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
        ZonedDateTime createdTime = ZonedDateTime.parse(receivedObjectBody.getString(Constants.BODY_KEY_CREATED_AT), DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)
                        .withZone(ZoneOffset.UTC))
                .truncatedTo(ChronoUnit.SECONDS);
        return Threshold.isEqual(currentTime.toEpochSecond(),createdTime.toEpochSecond());
    }

    public static boolean isUpdatedAtEqual(Response response) {
        JSONObject receivedObjectBody = new JSONObject(response.getBody().as(ExtendedUserObject.class));

        ZonedDateTime currentTime = ZonedDateTime.now(ZoneOffset.UTC)
                .truncatedTo(ChronoUnit.SECONDS);
        ZonedDateTime updatedTime = ZonedDateTime.parse(receivedObjectBody.getString(Constants.BODY_KEY_UPDATED_AT), DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)
                        .withZone(ZoneOffset.UTC))
                .truncatedTo(ChronoUnit.SECONDS);
        return Threshold.isEqual(currentTime.toEpochSecond(),updatedTime.toEpochSecond());
    }

    public static boolean isUserCreated(Response response) {
        return response.then().extract().statusCode() == 201;
    }
}
