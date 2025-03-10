package base.Objects.UserObjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExtendedUserObject<T> extends BaseUserObject {

    T id;
    String first_name;
    String last_name;
    String email;
    String avatar;

    public ExtendedUserObject(String email) {
        this.email = email;
    }

    public ExtendedUserObject(T id) {
        this.id = id;
    }
}
