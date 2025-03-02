package APITests;

import base.Common.GenericChecks;
import base.Common.UserChecks.UserChecks;
import base.Constants;
import base.Steps.CreateUserSteps;
import base.Objects.UserObjects.BaseUserObject;
import base.Objects.UserObjects.ExtendedUserObject;
import base.Utils.FakerData;
import io.restassured.RestAssured;

import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.List;
import java.util.stream.Stream;

import static base.Steps.CreateUserSteps.getLastCreatedUser;
import static base.Utils.ParserHelper.getJsonAsObjectUsingGson;

@Execution(ExecutionMode.CONCURRENT)
public class UserTests {

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = Constants.BASE_URL;
    }

    @TestFactory
    Stream<DynamicTest> checkPostUserRequest() {
        List<BaseUserObject> userDataList = getJsonAsObjectUsingGson(Constants.VALID_JSON_FILE_PATH, BaseUserObject[].class);
        return  userDataList.stream().map(
                instance ->
                DynamicTest.dynamicTest(String.format("Verification of: %s %s user", instance.getName(), instance.getJob()), () ->
                        checkPostUser(instance)));
    }

    @TestFactory
    Stream<DynamicTest> checkGetUserRequest() {
        List<ExtendedUserObject> userDataList = CreateUserSteps.getAllUsers();
        return userDataList.stream().map(
                instance -> DynamicTest.dynamicTest(String.format("Verification of: %s %s user", instance.getFirst_name(), instance.getLast_name()), () ->
                        checkSpecificUser(Integer.parseInt(instance.getId())))
        );
    }

    @TestFactory
    Stream<DynamicTest> checkPostRandomUser() {
        List<BaseUserObject> userDataList = FakerData.createFakerUserList(10);
        return userDataList.stream().map(
                instance -> DynamicTest.dynamicTest(String.format("Verification of: %s %s random user", instance.getName(), instance.getJob()), () ->
                        checkPostUser(instance))
        );
    }

    // Does not work as intended due to the fact that POST request with Invalid user Data goes through with 200 statusCode
    @TestFactory
    Stream<DynamicTest> checkPostInvalidRequest() {
        List<BaseUserObject> userDataList = getJsonAsObjectUsingGson(Constants.INVALID_JSON_FILE_PATH, BaseUserObject[].class);
        return  userDataList.stream().map(
                instance ->
                        DynamicTest.dynamicTest(String.format("Verification of Invalid user: %s %s user", instance.getName(), instance.getJob()), () ->
                                checkPostUser(instance)));
    }

    @Test
    public void checkGetAllUsers() {
        Response response = CreateUserSteps.getAllUsersRequest();
        Assertions.assertTrue(GenericChecks.isGetRequestValid(response));
    }

    @Test
    public void checkGetInvalidUser() {
        Response response = CreateUserSteps.getUser(Integer.parseInt(getLastCreatedUser().getId()) + 1);
        Assertions.assertTrue(GenericChecks.isElementNotFound(response));
    }

    public void checkSpecificUser(int userID) {
        Response response = CreateUserSteps.getUser(userID);
        Assertions.assertTrue(GenericChecks.isGetRequestValid(response));
        Assertions.assertFalse(GenericChecks.isElementNotFound(response));
    }

    public void checkPostUser(BaseUserObject user) {
        Response response = CreateUserSteps.postNewUserWithResponse(user);
        Assertions.assertTrue(UserChecks.isUserCreated(response));
        Assertions.assertTrue(UserChecks.isCreatedAtEqual(response));
    }
}
