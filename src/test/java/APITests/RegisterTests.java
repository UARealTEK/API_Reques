package APITests;

import base.Common.GenericChecks;
import base.Common.RegisterChecks.RegisterChecks;
import base.Constants;
import base.Objects.RegisterObjects.RegisterObject;
import base.Objects.UserObjects.ExtendedUserObject;
import base.Steps.CreateUserSteps;
import base.Utils.Endpoints;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.stream.Stream;

import static base.Steps.CreateRegisterSteps.*;
import static io.restassured.RestAssured.given;

public class RegisterTests {


    private static final Log log = LogFactory.getLog(RegisterTests.class);

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

    @Test
    public void checkPostUserWithoutPassword() {
        ExtendedUserObject object = new ExtendedUserObject(getRandomRegisteredUser().getEmail());

        Assertions.assertTrue(GenericChecks.isRequestInvalid(
                given()
                        .contentType(ContentType.JSON)
                        .body(object)
                        .post(Endpoints.getEndpoint(Endpoints.REGISTER)))
        );
    }

    @Test
    public void checkPostUserWithoutEmail() {
        RegisterObject object = new RegisterObject(new Faker().internet().password());

        Assertions.assertTrue(GenericChecks.isRequestInvalid(
                given()
                        .contentType(ContentType.JSON)
                        .body(object)
                        .post(Endpoints.getEndpoint(Endpoints.REGISTER)))
        );
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
