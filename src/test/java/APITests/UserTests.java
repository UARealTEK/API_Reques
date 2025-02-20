package APITests;

import base.Constants;
import base.CreateUserObject;
import base.CreateUserSteps;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UserTests {

    private static CreateUserObject object; // user Method for deserealiztion here

    @Test
    public void checkPostUser(CreateUserObject object) {
        CreateUserObject actualObject;
        CreateUserSteps steps = new CreateUserSteps();
        steps.postNewUserWithResponse(object);
        LocalDate currentTime = LocalDate.now();




        currentTime.compareTo(LocalDate.parse(actualObject.getCreatedAt(), Constants.DATE_FORMAT));

    }

    /**
     * TODO:
     * - create method which will parse JSON to CreateUserObject Object (deserialize JSON)
     *
     * Steps for Tests:
     * 1. Create UserSteps Object (deserialize JSON to Object)
     * 2. execute method using the received data (post....)
     * 3. Receive the response and .extract().body()....getObject() - > to receive new CreateUserObject()
     * 4. Compare tje received Body (after executing POST request) with the object which was passed into the test
     * 5.
     *
     */
}
