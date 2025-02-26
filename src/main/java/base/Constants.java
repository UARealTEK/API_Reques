package base;

import base.utils.Endpoints;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Constants {
    public static String BASE_URL = "https://reqres.in/";
    public static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSX";
    public static String JSON_FILE_PATH = "src/resources/user.json";

    public static Path getJSONFilePath () {
        return Paths.get(JSON_FILE_PATH);
    }

    public static void writeNewObjectToJSON(JSONObject object) throws IOException {
        String content = "";

        if (Files.exists(getJSONFilePath())) {
            content = new String(Files.readAllBytes(getJSONFilePath()));
        }

        JSONArray array = content.isEmpty() ? new JSONArray() : new JSONArray(content);

        array.put(object);
        Files.write(getJSONFilePath(), array.toString(2).getBytes());
    }
}
