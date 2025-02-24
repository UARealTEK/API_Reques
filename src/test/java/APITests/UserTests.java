package APITests;

import base.Constants;
import base.CreateUserObject;
import base.CreateUserSteps;
import io.restassured.RestAssured;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTests {

    private static final Log log = LogFactory.getLog(UserTests.class);
    private CreateUserObject object;

    @BeforeEach
    void setUp() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        List<CreateUserObject> objects = CreateUserObject.getObjectsFromJson(Constants.JSON_FILE_PATH);

        if (objects == null) {
            throw new IllegalStateException("Unable to locate objects in JSON file!");
        }

        object = objects.get(random.nextInt(objects.size()));
        log.info(String.format("object contains the following data: %s", object));

        if (object == null) {
            throw new IllegalStateException("No users found in the JSON file!");
        }
        RestAssured.baseURI = Constants.BASE_URL;
    }

    @Test
    public void checkPostUser() throws IOException {
        CreateUserObject actualObject = object;
        LocalDate currentTime = LocalDate.now();
        CreateUserSteps.postNewUser(object);


        log.info(CreateUserSteps.postNewUserWithResponse(object));
        assertTrue(currentTime.isEqual(LocalDate.parse(actualObject.getCreatedAt(), DateTimeFormatter.ofPattern(Constants.DATE_FORMAT))));
    }
}
