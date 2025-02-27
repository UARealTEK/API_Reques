package base.Utils;

import base.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.response.Response;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class ParserHelper {

    @SneakyThrows
    public static <T> List<T> getJsonAsObjectUsingGson(Class<T[]> cl) {
        String files = new String(Files.readAllBytes(Constants.getJSONFilePath()));
        return Arrays.asList(new Gson().fromJson(files,cl));
    }

    @SneakyThrows
    public static <T> List<T> getJsonAsObjectUsingGson(String jsonFilePath, Class<T[]> cl) {
        String jsonSting = new String(Files.readAllBytes(Paths.get((System.getProperty("src/resources/") + jsonFilePath))));
        return Arrays.asList(new Gson().fromJson(jsonSting,cl));
    }

    @SneakyThrows
    public static <T> List<T> parseResponseToList(Response response, Class<T[]> clazz) {
        String responseBody = response.getBody().asString();
        return new Gson().fromJson(responseBody, TypeToken.getParameterized(List.class, clazz).getType());
    }




}
