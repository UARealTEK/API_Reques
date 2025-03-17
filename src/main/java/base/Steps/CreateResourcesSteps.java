package base.Steps;

import base.Common.Constants.ConstantKeys;
import base.Objects.ResourceObjects.Resources;
import base.Utils.Endpoints;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static io.restassured.RestAssured.given;

public class CreateResourcesSteps {

    public static List<Resources> getAllResources() {
        List<Resources> allResources = new ArrayList<>();
        int currentPage = 1;
        int totalPages;

        do {
            Response response = given()
                    .queryParam(ConstantKeys.QUERY_PARAM_PAGE,currentPage)
                    .get(Endpoints.getEndpoint(Endpoints.RESOURSES));

            List<Resources> resources = response.jsonPath().getList(ConstantKeys.BODY_KEY_DATA, Resources.class);
            allResources.addAll(resources);
            currentPage++;
            totalPages = response.jsonPath().getInt(ConstantKeys.RESPONSE_KEY_TOTAL_PAGES);
        } while (currentPage <= totalPages);

        return allResources;
    }

    public static Response getAllResourcesRequest() {
        return given()
                .get(Endpoints.getEndpoint(Endpoints.RESOURSES));
    }

    public static Response getResource(int ResourceId) {
        return given()
                .queryParam(ConstantKeys.QUERY_PARAM_ID, ResourceId)
                .get(Endpoints.getEndpoint(Endpoints.RESOURSES));
    }

    public static Resources getLastResource() {
        return getAllResources().stream().max(Comparator.comparingInt(Resources::getId)).orElse(null);
    }

}
