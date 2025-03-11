package APITests;

import base.Common.GenericChecks;
import base.Common.RegisterChecks.RegisterChecks;
import base.Constants;
import base.Objects.RegisterObjects.RegisterObject;
import base.Objects.UserObjects.ExtendedUserObject;
import base.Steps.CreateUserSteps;
import base.Utils.Endpoints;
import com.github.javafaker.Faker;
import com.google.gson.reflect.TypeToken;
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
        List<RegisterObject<Integer>> users = getAllRegisteredUsers(Integer.class);
        return users.stream().map(user ->
            DynamicTest.dynamicTest(String.format("checking for user: %s", user.getName()), () ->
                    checkGetSingleRegisteredUser(user)));
    }

    @TestFactory
    Stream<DynamicTest> checkPostRegisterUser() {
        List<ExtendedUserObject<Integer>> list = CreateUserSteps.getAllUsers();
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
        ExtendedUserObject<Integer> object = new ExtendedUserObject<>(getRandomRegisteredUser().getEmail());

        Assertions.assertTrue(GenericChecks.isRequestInvalid(
                given()
                        .contentType(ContentType.JSON)
                        .body(object)
                        .post(Endpoints.getEndpoint(Endpoints.REGISTER)))
        );
    }

    @Test
    public void checkPostUserWithoutEmail() {
        Assertions.assertTrue(GenericChecks.isRequestInvalid(
                given()
                        .contentType(ContentType.JSON)
                        .body(new Faker().internet().password())
                        .post(Endpoints.getEndpoint(Endpoints.REGISTER)))
        );
    }

    public void checkGetSingleRegisteredUser(RegisterObject<Integer> user) {
        Response response = getRegisteredUser(user.getId());
        Assertions.assertTrue(GenericChecks.isRequestValid(response));
    }

    public void checkPostRegisterUser(ExtendedUserObject<Integer> body) {
        Response response = postRegister(body);
        Assertions.assertTrue(GenericChecks.isRequestValid(response));
        Assertions.assertTrue(RegisterChecks.isIDMatchedWithResponseID(response,body));
    }
}
