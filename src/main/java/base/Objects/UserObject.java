package base.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserObject {
    String id;
    String name;
    String job;
    String createdAt;
    String first_name;
    String last_name;
    String email;
    String avatar;


}
