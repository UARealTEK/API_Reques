package APITests;

import base.Constants;
import base.CreateUserObject;
import base.CreateUserSteps;
import base.utils.Threshold;
import io.restassured.RestAssured;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import java.io.IOException;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTests {

    private static final Log log = LogFactory.getLog(UserTests.class);
    private JSONObject object;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = Constants.BASE_URL;
    }

    @RepeatedTest(3)
    public void checkPostUser() throws IOException {
        object = CreateUserSteps.postNewUserWithResponse(CreateUserObject.getRandomObjectFromJson());
        String objectCreatedAt = object.getString("createdAt");


        ZonedDateTime currentTime = ZonedDateTime.now(ZoneOffset.UTC)
                .truncatedTo(ChronoUnit.SECONDS);
        ZonedDateTime createdTime = ZonedDateTime.parse(objectCreatedAt, DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)
                .withZone(ZoneOffset.UTC))
                .truncatedTo(ChronoUnit.SECONDS);

        log.info(createdTime.toEpochSecond());
        log.info(currentTime.toEpochSecond());

        assertTrue(Threshold.isEqual(createdTime.toEpochSecond(),currentTime.toEpochSecond()));
    }
}
