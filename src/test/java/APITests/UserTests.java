package APITests;

import base.Constants;
import base.CreateUserObject;
import base.CreateUserSteps;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTests {

    private CreateUserObject object;

    @BeforeEach
    void setUp() {
        object = CreateUserSteps.postNewUser(object);
        if (object == null) {
            throw new IllegalStateException("No users found in the JSON file!");
        }
        RestAssured.baseURI = Constants.BASE_URL;
    }

    @Test
    public void checkPostUser() {
        CreateUserObject actualObject = CreateUserSteps.postNewUser(object);

        LocalDate currentTime = LocalDate.now();
        assertTrue(currentTime.isEqual(LocalDate.parse(actualObject.getCreatedAt(), DateTimeFormatter.ofPattern(Constants.DATE_FORMAT))));
    }
}
