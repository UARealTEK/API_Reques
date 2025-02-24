package APITests;

import base.Constants;
import base.CreateUserObject;
import base.CreateUserSteps;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTests {

    private CreateUserObject object;

    @BeforeEach
    void setUp() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        List<CreateUserObject> objects = CreateUserObject.getObjectsFromJson(Constants.JSON_FILE_PATH);

        if (objects == null) {
            throw new IllegalStateException("Unable to locate objects in JSON file!");
        }

        object = objects.get(random.nextInt(objects.size()));

        if (object == null) {
            throw new IllegalStateException("No users found in the JSON file!");
        }
        RestAssured.baseURI = Constants.BASE_URL;
    }

    @Test
    public void checkPostUser() {
        CreateUserObject actualObject = object;
        LocalDate currentTime = LocalDate.now();
        assertTrue(currentTime.isEqual(LocalDate.parse(actualObject.getCreatedAt(), DateTimeFormatter.ofPattern(Constants.DATE_FORMAT))));
    }
}
