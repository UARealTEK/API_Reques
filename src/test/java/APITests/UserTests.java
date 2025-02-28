package APITests;

import base.Common.Checks;
import base.Constants;
import base.CreateUserSteps;
import base.Objects.BaseUserObject;
import base.Objects.ExtendedUserObject;
import base.Utils.FakerUser;
import io.restassured.RestAssured;

import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.List;
import java.util.stream.Stream;

import static base.Utils.ParserHelper.getJsonAsObjectUsingGson;

@Execution(ExecutionMode.CONCURRENT)
public class UserTests {

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = Constants.BASE_URL;
    }

    @TestFactory
    Stream<DynamicTest> checkPostUserRequest() {
        List<BaseUserObject> userDataList = getJsonAsObjectUsingGson(BaseUserObject[].class);
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
                        checkSpecificUser(instance))
        );
    }

    @TestFactory
    Stream<DynamicTest> checkPostRandomUser() {
        List<BaseUserObject> userDataList = FakerUser.createFakerUserList();
        return userDataList.stream().map(
                instance -> DynamicTest.dynamicTest(String.format("Verification of: %s %s random user", instance.getName(), instance.getJob()), () ->
                        checkPostUser(instance))
        );
    }

    @Test
    public void checkGetAllUsers() {
        Response response = CreateUserSteps.getAllUsersRequest();
        Assertions.assertTrue(Checks.isGetRequestValid(response));
    }

    public void checkSpecificUser(ExtendedUserObject user) {
        Response response = CreateUserSteps.getUser(user);
        Assertions.assertTrue(Checks.isGetRequestValid(response));
    }

    public void checkPostUser(BaseUserObject user) {
        Response response = CreateUserSteps.postNewUserWithResponse(user);
        Assertions.assertTrue(Checks.isUserCreated(response));
        Assertions.assertTrue(Checks.isCreatedAtEqual(response));
    }
}
