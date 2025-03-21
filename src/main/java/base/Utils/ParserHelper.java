package base.Utils;

import base.Common.Constants.ConstantKeys;
import com.google.gson.Gson;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class ParserHelper {

    @SneakyThrows
    public static <T> List<T> getJsonAsObjectUsingGson(String jsonFilePath, Class<T[]> cl) {
        String jsonString = new String(Files.readAllBytes(Paths.get(ConstantKeys.RESOURCES_PATH + jsonFilePath)));
        return Arrays.asList(new Gson().fromJson(jsonString, cl));
    }

}
