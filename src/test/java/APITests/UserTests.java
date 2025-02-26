package APITests;

import base.Constants;
import base.CreateUserObject;
import base.CreateUserSteps;
import base.utils.Threshold;
import io.restassured.RestAssured;

import io.restassured.response.Response;
import io.restassured.specification.Argument;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;

import java.nio.file.Files;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Execution(ExecutionMode.CONCURRENT)
public class UserTests {

    private static final Log log = LogFactory.getLog(UserTests.class);

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = Constants.BASE_URL;
    }

    @ParameterizedTest()
    @MethodSource("userArguments")
    public void checkPostUser(String name, String job) throws IOException {
        JSONObject object = new JSONObject().put("name", name).put("job",job);
        JSONObject response = CreateUserSteps.postNewUserWithResponse(object);


        ZonedDateTime currentTime = ZonedDateTime.now(ZoneOffset.UTC)
                .truncatedTo(ChronoUnit.SECONDS);
        ZonedDateTime createdTime = ZonedDateTime.parse(response.getString("createdAt"), DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)
                .withZone(ZoneOffset.UTC))
                .truncatedTo(ChronoUnit.SECONDS);

        log.info(currentTime + " / " + createdTime);

        assertTrue(Threshold.isEqual(createdTime.toEpochSecond(),currentTime.toEpochSecond()));

    }

    public static Stream<Arguments> userArguments() throws IOException {
        String files = new String(Files.readAllBytes(Constants.getJSONFilePath()));

        JSONArray array = new JSONArray(files);

        return array.toList().stream()
                .map(user -> {
                    if (user instanceof Map<?, ?>) {
                        Map<String, Object> userMap = (Map<String, Object>) user;
                        String name = (String) userMap.get("name");
                        String job = (String) userMap.get("job");
                        return Arguments.of(name, job);
                    } else {
                        return Arguments.of("", "");
                    }
                });
    }
}
