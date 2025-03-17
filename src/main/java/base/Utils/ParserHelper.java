package base.Utils;

import base.Constants;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.gson.Gson;
import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class ParserHelper {

    @SneakyThrows
    public static <T> List<T> getJsonAsObjectUsingGson(String jsonFilePath, Class<T[]> cl) {
        String jsonString = new String(Files.readAllBytes(Paths.get(Constants.RESOURCES_PATH + jsonFilePath)));
        return Arrays.asList(new Gson().fromJson(jsonString, cl));
    }

}
