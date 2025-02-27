package base.utils;

import base.Constants;
import com.google.gson.Gson;
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
        String jsonSting = new String(Files.readAllBytes(Paths.get((System.getProperty("user.dir") + jsonFilePath))));
        return Arrays.asList(new Gson().fromJson(jsonSting,cl));

    }
}
