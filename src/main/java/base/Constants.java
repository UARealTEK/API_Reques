package base;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Constants {
    public static final String BASE_URL = "https://reqres.in/";
    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSX";
    public static final String JSON_FILE_PATH = "src/resources/user.json";
    public static final String RESOURCES_PATH = "src/resources/";

    public static final String BODY_KEY_DATA = "data";
    public static final String QUERY_PARAM_ID = "id";


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
