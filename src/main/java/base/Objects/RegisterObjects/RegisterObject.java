package base.Objects.RegisterObjects;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterObject<T> {

    T id;
    String name;
    int year;
    String color;
    String pantone_value;
}
