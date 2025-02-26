package base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

@Data
@AllArgsConstructor
public class CreateUserObject {

    private static final Log log = LogFactory.getLog(CreateUserObject.class);

    public static JSONArray getObjectsFromJson() throws IOException {
        String jsonString = Files.readString(Paths.get(Constants.JSON_FILE_PATH), StandardCharsets.UTF_8);

        return new JSONArray(jsonString);
    }

    public static JSONObject getRandomObjectFromJson() throws IOException {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        JSONArray array = getObjectsFromJson();

        return array.getJSONObject(random.nextInt(array.length()));
    }

    public String getJsonFromObject() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }

}
