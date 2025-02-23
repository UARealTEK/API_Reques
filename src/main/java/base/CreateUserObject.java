package base;

import lombok.Data;
import lombok.*;

@Data
@AllArgsConstructor
public class CreateUserObject {

    private String job;
    private String name;
    private Integer id;
    private String createdAt;

}
