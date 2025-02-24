package APITests;

import base.Constants;
import base.CreateUserObject;
import base.CreateUserSteps;
import base.utils.Threshold;
import io.restassured.RestAssured;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTests {

    private static final Log log = LogFactory.getLog(UserTests.class);
    private CreateUserObject object;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = Constants.BASE_URL;
    }

    @RepeatedTest(3)
    public void checkPostUser() throws IOException {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        List<CreateUserObject> objects = CreateUserObject.getObjectsFromJson(Constants.JSON_FILE_PATH);

        if (objects == null) {
            throw new IllegalStateException("Unable to locate objects in JSON file!");
        }

        object = CreateUserSteps.postNewUserWithResponse(objects.get(random.nextInt(objects.size())));

        ZonedDateTime currentTime = ZonedDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.SECONDS);
        ZonedDateTime createdTime = ZonedDateTime.parse(object.getCreatedAt(), DateTimeFormatter.ofPattern(Constants.DATE_FORMAT).withZone(ZoneOffset.UTC)).truncatedTo(ChronoUnit.SECONDS);

        log.info(createdTime.toEpochSecond());
        log.info(currentTime.toEpochSecond());

        assertTrue(Threshold.isEqual(createdTime.toEpochSecond(),currentTime.toEpochSecond()));
    }
}
