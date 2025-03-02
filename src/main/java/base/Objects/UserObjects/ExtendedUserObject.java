package base.Objects.UserObjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExtendedUserObject extends BaseUserObject {

    String id;
    String createdAt;
    String first_name;
    String last_name;
    String email;
    String avatar;

}
