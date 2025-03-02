package APITests;

import base.Common.GenericChecks;
import base.Constants;
import base.Objects.ResourceObjects.Resources;
import base.Steps.CreateResourcesSteps;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.stream.Stream;

public class ResourcesTests {

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = Constants.BASE_URL;
    }

    @TestFactory
    Stream<DynamicTest> checkGetResources() {
        List<Resources> resourcesList = CreateResourcesSteps.getAllResources();
        return resourcesList.stream().map(resource ->
                DynamicTest.dynamicTest(String.format("Checking resource %s", resource.getName()), () ->
                        checkGetResource(resource.getId())));
    }

    @Test
    public void checkGetAllResources() {
        Assertions.assertTrue(GenericChecks.isGetRequestValid(CreateResourcesSteps.getAllResourcesRequest()));
    }

    @Test
    public void checkGetInvalidResource() {
        Response response = CreateResourcesSteps.getResource(CreateResourcesSteps.getLastResource().getId() + 1);
        Assertions.assertTrue(GenericChecks.isElementNotFound(response));
    }

    public void checkGetResource(int resourceID) {
        Response response = CreateResourcesSteps.getResource(resourceID);
        Assertions.assertTrue(GenericChecks.isGetRequestValid(response));
    }
}
