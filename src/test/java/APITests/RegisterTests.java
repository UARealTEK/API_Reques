package APITests;

import base.Common.GenericChecks;
import base.Common.RegisterChecks.RegisterChecks;
import base.Constants;
import base.Objects.RegisterObjects.RegisterObject;
import base.Objects.UserObjects.ExtendedUserObject;
import base.Steps.CreateUserSteps;
import base.Utils.Endpoints;
import com.github.javafaker.Faker;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.List;
import java.util.stream.Stream;

import static base.Steps.CreateRegisterSteps.*;
import static io.restassured.RestAssured.given;

@Execution(ExecutionMode.CONCURRENT)
public class RegisterTests {


    @BeforeEach
    void setUp() {
        RestAssured.baseURI = Constants.BASE_URL;
    }

    @TestFactory
    @Severity(SeverityLevel.CRITICAL)
    @Description("This test verifies retrieving single registered User")
    @Story("Register Feature")
    @Feature("User Registration")
    Stream<DynamicTest> checkGetRegisteredUser() {
        List<RegisterObject> users = getAllRegisteredUsers();
        return users.stream().map(user ->
            DynamicTest.dynamicTest(String.format("checking for user: %s", user.getName()), () ->
                    checkGetSingleRegisteredUser(user)));
    }

    @TestFactory
    @Severity(SeverityLevel.CRITICAL)
    @Description("This test verifies registered User")
    @Story("Register Feature")
    @Feature("User Registration")
    Stream<DynamicTest> checkPostRegisterUser() {
        List<ExtendedUserObject> list = CreateUserSteps.getAllUsers();
        return list.stream().map(registeredUser ->
                DynamicTest.dynamicTest(String.format("Checking post for id: %s and name %s", registeredUser.getId(),registeredUser.getEmail()), () ->
                        checkPostRegisterUser(registeredUser)));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("This test verifies retrieving all registered Users")
    @Story("Register Feature")
    @Feature("User Registration")
    public void checkGetAllRegisteredUsers() {
        Assertions.assertTrue(GenericChecks.isRequestValid(getAllRegisteredUsersRequest()));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("This test verifies attempting to register without setting up a password")
    @Story("Register Feature")
    @Feature("User Registration")
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
    @Severity(SeverityLevel.CRITICAL)
    @Description("This test verifies attempting to register without setting up an email")
    @Story("Register Feature")
    @Feature("User Registration")
    public void checkPostUserWithoutEmail() {
        Assertions.assertTrue(GenericChecks.isRequestInvalid(
                given()
                        .contentType(ContentType.JSON)
                        .body(new Faker().internet().password())
                        .post(Endpoints.getEndpoint(Endpoints.REGISTER)))
        );
    }

    public void checkGetSingleRegisteredUser(RegisterObject user) {
        Response response = getRegisteredUser(Integer.parseInt(user.getId()));
        Assertions.assertTrue(GenericChecks.isRequestValid(response));
    }

    public void checkPostRegisterUser(ExtendedUserObject body) {
        Response response = postRegister(body);
        Assertions.assertTrue(GenericChecks.isRequestValid(response));
        Assertions.assertTrue(RegisterChecks.isIDMatchedWithResponseID(response,body));
    }
}
