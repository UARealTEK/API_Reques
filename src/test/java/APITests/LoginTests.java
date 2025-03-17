package APITests;

import base.Common.Constants.ResponseErrorMessages;
import base.Common.GenericChecks;
import base.Common.Constants.ConstantKeys;
import base.Objects.LoginObjects.LoginObject;
import base.Objects.RegisterObjects.RegisterObject;
import base.Objects.UserObjects.ExtendedUserObject;
import base.Steps.CreateLoginSteps;
import base.Steps.CreateUserSteps;
import base.Utils.Endpoints;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.List;
import java.util.stream.Stream;

import static base.Steps.CreateLoginSteps.*;
import static io.restassured.RestAssured.given;

@Execution(ExecutionMode.CONCURRENT)
public class LoginTests {

    private static final Log log = LogFactory.getLog(LoginTests.class);

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = ConstantKeys.BASE_URL;
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

    @TestFactory
    @Severity(SeverityLevel.NORMAL)
    @Description("This test verifies user login attempt without providing a password")
    @Story("Login Feature")
    @Feature("User Authentication")
    Stream<DynamicTest> postInvalidLoginUser() {
        List<ExtendedUserObject> loggedInUserList = CreateUserSteps.getAllUsers();
        return loggedInUserList.stream()
                .map(user ->
                        DynamicTest.dynamicTest(String.format("Checking login user %s", user.getEmail()), () -> checkLoginWithoutPassword(user)));
    }

    public void checkGetSingleUser(RegisterObject user) {
        Response response = getLoggedInUser(Integer.parseInt(user.getId()));
        Assertions.assertTrue(GenericChecks.isRequestValid(response));
    }

    public void checkUserLogin(RegisterObject user) {
        Response response = logInExistingUser(Integer.parseInt(user.getId()));
        Assertions.assertTrue(GenericChecks.isRequestValid(response));
    }

    public void checkLoginWithoutPassword(ExtendedUserObject object) {
        LoginObject emptyPasswordUser = new LoginObject(object.getEmail(), "");
        LoginObject nullPasswordUser = new LoginObject(object.getEmail(), null);
        Response responseForEmptyStringPassword = given()
                .contentType(ContentType.JSON)
                .body(emptyPasswordUser)
                .post(Endpoints.getEndpoint(Endpoints.LOGIN));

        Response responseForNullPassword = given()
                .contentType(ContentType.JSON)
                .body(nullPasswordUser)
                .post(Endpoints.getEndpoint(Endpoints.LOGIN));

        Assertions.assertTrue(GenericChecks.isRequestInvalid(responseForEmptyStringPassword));
        Assertions.assertEquals(ResponseErrorMessages.MISSING_PASSWORD_MESSAGE, responseForEmptyStringPassword.jsonPath().get(ConstantKeys.RESPONSE_KEY_ERROR));
        Assertions.assertTrue(GenericChecks.isRequestInvalid(responseForNullPassword));
        Assertions.assertEquals(ResponseErrorMessages.MISSING_PASSWORD_MESSAGE, responseForNullPassword.jsonPath().get(ConstantKeys.RESPONSE_KEY_ERROR));
    }
}
