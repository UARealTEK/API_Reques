package base.Objects.UserObjects;

public class ExtendedUserObjectFactory {

    public static <T> ExtendedUserObject<T> createExtendedUserObject(T id) {
        return new ExtendedUserObject<>(id);
    }
}
