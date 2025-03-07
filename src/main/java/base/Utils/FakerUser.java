package base.Utils;

import base.Objects.BaseUserObject;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static base.CreateUserSteps.getAllUsers;

public class FakerUser {

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
