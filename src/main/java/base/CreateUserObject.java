package base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserObject {

    private static final Log log = LogFactory.getLog(CreateUserObject.class);
    private String job;
    private String name;
    private Integer id;
    private String createdAt;

    public static CreateUserObject getObjectFromJson(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));
            return objectMapper.readValue(jsonContent, CreateUserObject.class);
        } catch (IOException e) {
            log.info(e.getStackTrace());
            return null;
        }
    }

    public String getJsonFromObject() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }

}
