package APITests;

import base.Common.GenericChecks;
import base.Constants;
import base.Objects.RegisterObjects.RegisterObject;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.List;
import java.util.stream.Stream;

import static base.Steps.CreateLoginSteps.*;

@Execution(ExecutionMode.CONCURRENT)
public class LoginTests {

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = Constants.BASE_URL;
    }


    @TestFactory
    @Severity(SeverityLevel.CRITICAL)
    @Description("This test verifies receiving user which was already logged in")
    @Story("Login Feature")
    @Feature("User Authentication")
    Stream<DynamicTest> getSingleLoggedInUser() {
        List<RegisterObject> registeredUsersList = getAllLoggedInUsers();
        return registeredUsersList.stream()
                .map(object ->
                        DynamicTest.dynamicTest(String.format("Checking user: %s", object.getName()), () -> checkGetSingleUser(object)));
    }

    @TestFactory
    @Severity(SeverityLevel.NORMAL)
    @Description("This test verifies single user login functionality")
    @Story("Login Feature")
    @Feature("User Authentication")
    Stream<DynamicTest> postLoginUser() {
        List<RegisterObject> registeredUsersList = getAllLoggedInUsers();
        return registeredUsersList.stream()
                .map(object ->
                        DynamicTest.dynamicTest(String.format("Checking user: %s", object.getName()), () -> checkUserLogin(object)));
    }

    public void checkGetSingleUser(RegisterObject user) {
        Response response = getLoggedInUser(Integer.parseInt(user.getId()));
        Assertions.assertTrue(GenericChecks.isRequestValid(response));
    }

    public void checkUserLogin(RegisterObject user) {
        Response response = logInExistingUser(Integer.parseInt(user.getId()));
        Assertions.assertTrue(GenericChecks.isRequestValid(response));
    }
}
