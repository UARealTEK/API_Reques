package APITests;

import base.Common.GenericChecks;
import base.Constants;
import base.Objects.ResourceObjects.Resources;
import base.Steps.CreateResourcesSteps;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.List;
import java.util.stream.Stream;

@Execution(ExecutionMode.CONCURRENT)
public class ResourcesTests {

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = Constants.BASE_URL;
    }

    @TestFactory
    @Severity(SeverityLevel.CRITICAL)
    @Description("This test verifies getting each resource")
    @Story("Resources Feature")
    @Feature("Resources")
    Stream<DynamicTest> checkGetResources() {
        List<Resources> resourcesList = CreateResourcesSteps.getAllResources();
        return resourcesList.stream().map(resource ->
                DynamicTest.dynamicTest(String.format("Checking resource %s", resource.getName()), () ->
                        checkGetResource(resource.getId())));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("This test verifies getting all resources")
    @Story("Resources Feature")
    @Feature("Resources")
    public void checkGetAllResources() {
        Assertions.assertTrue(GenericChecks.isRequestValid(CreateResourcesSteps.getAllResourcesRequest()));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("This test verifies getting invalid resource")
    @Story("Resources Feature")
    @Feature("Resources")
    public void checkGetInvalidResource() {
        Response response = CreateResourcesSteps.getResource(CreateResourcesSteps.getLastResource().getId() + 1);
        Assertions.assertTrue(GenericChecks.isElementNotFound(response));
    }

    public void checkGetResource(int resourceID) {
        Response response = CreateResourcesSteps.getResource(resourceID);
        Assertions.assertTrue(GenericChecks.isRequestValid(response));
    }
}
