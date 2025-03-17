package APITests;

import base.Common.GenericChecks;
import base.Common.UserChecks.UserChecks;
import base.Common.Constants.ConstantKeys;
import base.Steps.CreateUserSteps;
import base.Objects.UserObjects.BaseUserObject;
import base.Objects.UserObjects.ExtendedUserObject;
import base.Utils.FakerData;
import io.qameta.allure.*;
import io.restassured.RestAssured;

import io.restassured.response.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.List;
import java.util.stream.Stream;

import static base.Steps.CreateUserSteps.getLastCreatedUser;
import static base.Utils.ParserHelper.getJsonAsObjectUsingGson;

@Execution(ExecutionMode.CONCURRENT)
public class UserTests {

    private static final Log log = LogFactory.getLog(UserTests.class);

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = ConstantKeys.BASE_URL;
    }

    @TestFactory
    @Severity(SeverityLevel.CRITICAL)
    @Description("This test verifies posting a user")
    @Story("Users Feature")
    @Feature("Users")
    Stream<DynamicTest> checkPostUserRequest() {
        List<BaseUserObject> userDataList = getJsonAsObjectUsingGson(ConstantKeys.VALID_JSON_FILE_PATH, BaseUserObject[].class);
        return  userDataList.stream().map(
                instance ->
                DynamicTest.dynamicTest(String.format("Verification of: %s %s user", instance.getName(), instance.getJob()), () ->
                        checkPostUser(instance)));
    }

    @TestFactory
    @Severity(SeverityLevel.CRITICAL)
    @Description("This test verifies getting all users")
    @Story("Users Feature")
    @Feature("Users")
    Stream<DynamicTest> checkGetUserRequest() {
        List<ExtendedUserObject> userDataList = CreateUserSteps.getAllUsers();
        return userDataList.stream().map(
                instance -> DynamicTest.dynamicTest(String.format("Verification of: %s %s user", instance.getFirst_name(), instance.getLast_name()), () ->
                        checkSpecificUser(Integer.parseInt(instance.getId())))
        );
    }

    @TestFactory
    @Severity(SeverityLevel.CRITICAL)
    @Description("This test verifies posting a random User")
    @Story("Users Feature")
    @Feature("Users")
    Stream<DynamicTest> checkPostRandomUser() {
        List<BaseUserObject> userDataList = FakerData.createFakerUserList(10);
        return userDataList.stream().map(
                instance -> DynamicTest.dynamicTest(String.format("Verification of: %s %s random user", instance.getName(), instance.getJob()), () ->
                        checkPostUser(instance))
        );
    }

    @TestFactory
    @Severity(SeverityLevel.CRITICAL)
    @Description("This test verifies posting an invalid User")
    @Story("Users Feature")
    @Feature("Users")
    Stream<DynamicTest> checkPostInvalidRequest() {
        List<BaseUserObject> userDataList = getJsonAsObjectUsingGson(ConstantKeys.INVALID_JSON_FILE_PATH, BaseUserObject[].class);
        return  userDataList.stream().map(
                instance ->
                        DynamicTest.dynamicTest(String.format("Verification of Invalid user: %s %s user", instance.getName(), instance.getJob()), () ->
                                checkPostUser(instance)));
    }

    @TestFactory
    @Severity(SeverityLevel.CRITICAL)
    @Description("This test verifies updating data for a specific user")
    @Story("Users Feature")
    @Feature("Users")
    Stream<DynamicTest> checkPutUser() {
        List<ExtendedUserObject> userDataList = CreateUserSteps.getAllUsers();
        return userDataList.stream().map(
                object -> DynamicTest.dynamicTest(String.format("Checking case for: %s", object.getFirst_name()), () ->
                        checkPutUser(object))
        );
    }


    @TestFactory
    @Severity(SeverityLevel.CRITICAL)
    @Description("This test verifies deleting User")
    @Story("Users Feature")
    @Feature("Users")
    Stream<DynamicTest> checkDeleteUser() {
        List<ExtendedUserObject> userDataList = CreateUserSteps.getAllUsers();
        return userDataList.stream().map(
                object -> DynamicTest.dynamicTest(String.format("Checking case for: %s", object.getFirst_name()), () ->
                        checkDeleteUser(object))
        );
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("This test verifies getting all users")
    @Story("Users Feature")
    @Feature("Users")
    public void checkGetAllUsers() {
        Response response = CreateUserSteps.getAllUsersRequest();
        Assertions.assertTrue(GenericChecks.isRequestValid(response));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("This test verifies getting invalid user")
    @Story("Users Feature")
    @Feature("Users")
    public void checkGetInvalidUser() {
        ExtendedUserObject object = getLastCreatedUser();
        Response response = CreateUserSteps.getUser(Integer.parseInt(object.getId() + 1));
        Assertions.assertTrue(GenericChecks.isElementNotFound(response));
    }

    public void checkSpecificUser(int userID) {
        Response response = CreateUserSteps.getUser(userID);
        Assertions.assertTrue(GenericChecks.isRequestValid(response));
        Assertions.assertFalse(GenericChecks.isElementNotFound(response));
    }

    public void checkPostUser(BaseUserObject user) {
        Response response = CreateUserSteps.postNewUserWithResponse(user);
        Assertions.assertTrue(UserChecks.isUserCreated(response));
        Assertions.assertTrue(UserChecks.isCreatedAtEqual(response));
    }

    public void checkPutUser(ExtendedUserObject user) {
        Response response = CreateUserSteps.putUser(user);
        log.fatal(response.then().log().body());
        ExtendedUserObject expectedUser = response.getBody().as(ExtendedUserObject.class);

        Assertions.assertTrue(GenericChecks.isRequestValid(response));
        Assertions.assertNotEquals(expectedUser.getJob(), user.getJob());
        Assertions.assertNotEquals(expectedUser.getName(), user.getName());
        Assertions.assertTrue(UserChecks.isUpdatedAtEqual(response));
    }

    public void checkDeleteUser(ExtendedUserObject user) {
        Response response = CreateUserSteps.deleteUser(user);
        Assertions.assertTrue(GenericChecks.isElementDeleted(response));
    }
}
