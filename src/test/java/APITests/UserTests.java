package APITests;

import base.Common.Checks;
import base.Constants;
import base.CreateUserSteps;
import base.Objects.UserObject;
import io.restassured.RestAssured;

import io.restassured.response.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.List;
import java.util.stream.Stream;

import static base.Utils.ParserHelper.getJsonAsObjectUsingGson;

@Execution(ExecutionMode.CONCURRENT)
public class UserTests {

    private static final Log log = LogFactory.getLog(UserTests.class);

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = Constants.BASE_URL;
    }

    @TestFactory
    Stream<DynamicTest> checkPostUserRequest() {
        List<UserObject> userDataList = getJsonAsObjectUsingGson(UserObject[].class);
        return  userDataList.stream().map(
                instance ->
                DynamicTest.dynamicTest(String.format("Verification of: %s %s user", instance.getName(), instance.getJob()), () ->
                        checkPostUser(instance)));
    }

    @TestFactory
    Stream<DynamicTest> checkGetUserRequest() {
        List<UserObject> userDataList = CreateUserSteps.getAllUsers();
        return userDataList.stream().map(
                instance -> DynamicTest.dynamicTest(String.format("Verification of: %s %s user", instance.getFirst_name(), instance.getLast_name()), () ->
                        checkSpecificUser(instance))
        );
    }

    @Test
    public void checkGetAllUsers() {
        Response response = CreateUserSteps.getAllUsersRequest();
        Assertions.assertTrue(Checks.isGetRequestValid(response));
    }

    public void checkSpecificUser(UserObject user) {
        Response response = CreateUserSteps.getUser(user);
        Assertions.assertTrue(Checks.isGetRequestValid(response));
    }

    public void checkPostUser(UserObject user) {
        Response response = CreateUserSteps.postNewUserWithResponse(user);
        log.info(response.then().log().all());
        Assertions.assertTrue(Checks.isUserCreated(response));
        Assertions.assertTrue(Checks.isCreatedAtEqual(response));
    }
}
