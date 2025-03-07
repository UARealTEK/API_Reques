package base.Objects.RegisterObjects;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterObject {

    int id;
    String email;
    String password;
    String name;
    int year;
    String color;
    String pantone_value;

    public RegisterObject(String password) {
        this.password = password;
    }
}
