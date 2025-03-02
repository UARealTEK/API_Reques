package base.Steps;

import base.Constants;
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
                    .queryParam("page",currentPage)
                    .get(Endpoints.getEndpoint(Endpoints.RESOURSES));

            List<Resources> resources = response.jsonPath().getList(Constants.BODY_KEY_DATA, Resources.class);
            allResources.addAll(resources);
            currentPage++;
            totalPages = response.jsonPath().getInt("total_pages");
        } while (currentPage <= totalPages);

        return allResources;
    }

    public static Response getAllResourcesRequest() {
        return given()
                .get(Endpoints.getEndpoint(Endpoints.RESOURSES));
    }

    public static Response getResource(int ResourceId) {
        return given()
                .queryParam(Constants.QUERY_PARAM_ID, ResourceId)
                .get(Endpoints.getEndpoint(Endpoints.RESOURSES));
    }

    public static Resources getLastResource() {
        return getAllResources().stream().max(Comparator.comparingInt(Resources::getId)).orElse(null);
    }

}
