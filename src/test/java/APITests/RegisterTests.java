package APITests;

import base.Common.GenericChecks;
import base.Common.RegisterChecks.RegisterChecks;
import base.Constants;
import base.Objects.RegisterObjects.RegisterObject;
import base.Objects.UserObjects.ExtendedUserObject;
import base.Steps.CreateUserSteps;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.stream.Stream;

import static base.Steps.CreateRegisterSteps.*;

public class RegisterTests {


    @BeforeEach
    void setUp() {
        RestAssured.baseURI = Constants.BASE_URL;
    }


    @TestFactory
    Stream<DynamicTest> checkGetRegisteredUser() {
        List<RegisterObject> users = getAllRegisteredUsers();
        return users.stream().map(user ->
            DynamicTest.dynamicTest(String.format("checking for user: %s", user.getName()), () ->
                    checkGetSingleRegisteredUser(user)));
    }

    @TestFactory
    Stream<DynamicTest> checkPostRegisterUser() {
        List<ExtendedUserObject> list = CreateUserSteps.getAllUsers();
        return list.stream().map(registeredUser ->
                DynamicTest.dynamicTest(String.format("Checking post for id: %s and name %s", registeredUser.getId(),registeredUser.getEmail()), () ->
                        checkPostRegisterUser(registeredUser)));
    }

    @Test
    public void checkGetAllRegisteredUsers() {
        Assertions.assertTrue(GenericChecks.isRequestValid(getAllRegisteredUsersRequest()));
    }

    public void checkGetSingleRegisteredUser(RegisterObject user) {
        Response response = getRegisteredUser(user.getId());
        Assertions.assertTrue(GenericChecks.isRequestValid(response));
    }

    public void checkPostRegisterUser(ExtendedUserObject body) {
        Response response = postRegister(body);
        Assertions.assertTrue(GenericChecks.isRequestValid(response));
        Assertions.assertTrue(RegisterChecks.isIDMatchedWithResponseID(response,body));
    }
}
