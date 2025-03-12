package APITests;

import base.Common.GenericChecks;
import base.Constants;
import base.Objects.RegisterObjects.RegisterObject;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.List;
import java.util.stream.Stream;

import static base.Steps.CreateLoginSteps.*;

public class LoginTests {

    private static final Log log = LogFactory.getLog(LoginTests.class);

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = Constants.BASE_URL;
    }

    @TestFactory
    Stream<DynamicTest> getSingleLoggedInUser() {
        List<RegisterObject<Integer>> registeredUsersList = getAllLoggedInUsers();
        return registeredUsersList.stream()
                .map(object ->
                        DynamicTest.dynamicTest(String.format("Checking user: %s", object.getName()), () -> checkGetSingleUser(object)));
    }

    @TestFactory
    Stream<DynamicTest> postLoginUser() {
        List<RegisterObject<Integer>> registeredUsersList = getAllLoggedInUsers();
        return registeredUsersList.stream()
                .map(object ->
                        DynamicTest.dynamicTest(String.format("Checking user: %s", object.getName()), () -> checkUserLogin(object)));
    }

    public void checkGetSingleUser(RegisterObject<Integer> user) {
        Response response = getLoggedInUser(user.getId());
        Assertions.assertTrue(GenericChecks.isRequestValid(response));
    }

    public void checkUserLogin(RegisterObject<Integer> user) {
        Response response = logInExistingUser(user.getId());
        log.info(response.then().log().body());
        Assertions.assertTrue(GenericChecks.isRequestValid(response));
    }
}
