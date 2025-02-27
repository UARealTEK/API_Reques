package APITests;

import base.Constants;
import base.CreateUserSteps;
import base.Objects.UserObject;
import io.restassured.RestAssured;

import io.restassured.response.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.provider.MethodSource;

//import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Stream;

import static base.utils.ParserHelper.getJsonAsObjectUsingGson;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @MethodSource("userArguments")
    public void checkPostUser(UserObject user) {
        Response response = CreateUserSteps.postNewUserWithResponse(user);
        JSONObject receivedObjectBody = new JSONObject(response.then().extract().body().asString());

        //TODO Commented due to the import issue
/*        ZonedDateTime currentTime = ZonedDateTime.now(ZoneOffset.UTC)
//                .truncatedTo(Chronology.SECONDS);
//        ZonedDateTime createdTime = ZonedDateTime.parse(receivedObjectBody.getString("createdAt"), DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)
//                .withZone(ZoneOffset.UTC))
//                .truncatedTo(ChronoUnit.SECONDS);
//        log.info(currentTime + " / " + createdTime);
//        assertTrue(Threshold.isEqual(createdTime.toEpochSecond(),currentTime.toEpochSecond())); */

        log.info(response.then().extract().statusCode());
        log.info(CreateUserSteps.getUser(new JSONObject(response.then().extract().body().asString()).getString("id")).then().extract().statusCode());
    }
}
