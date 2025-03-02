package base.Utils;

import base.Objects.ResourceObjects.Resources;
import base.Objects.UserObjects.BaseUserObject;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;

public class FakerData {

    public static BaseUserObject createFakerUser() {
        Faker faker = new Faker();
        return new BaseUserObject(faker.name().firstName(),faker.job().title());
    }

    public static List<BaseUserObject> createFakerUserList(int countOfGeneratedUsers) {
        List<BaseUserObject> list = new ArrayList<>();

        for (int i = 0; i < countOfGeneratedUsers; i++) {
            list.add(createFakerUser());
        }

        return list;
    }
}
