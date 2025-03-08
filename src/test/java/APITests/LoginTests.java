package APITests;

import base.Common.GenericChecks;
import base.Constants;
import base.Objects.LoginObjects.LoginObject;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.List;
import java.util.stream.Stream;

import static base.Steps.CreateLoginSteps.getAllLoggedInUsers;
import static base.Steps.CreateLoginSteps.getLoggedInUser;

public class LoginTests {

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = Constants.BASE_URL;
    }

    @TestFactory
    Stream<DynamicTest> getSingleUser() {
        List<LoginObject> loggedInUsersList = getAllLoggedInUsers();
        return loggedInUsersList.stream()
                .map(object ->
                        DynamicTest.dynamicTest(String.format("Checking user: %s", object.getName()), () -> checkGetSingleUser(object)));
    }

    public void checkGetSingleUser(LoginObject user) {
        Response response = getLoggedInUser(user.getId());
        Assertions.assertTrue(GenericChecks.isRequestValid(response));
    }
}
