package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserControllerTest {
    UserController userController;
    User user;
    private static Validator validator;

    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    @BeforeEach
    public void start() {
        userController = new UserController();
        user = new User();
        user.setName("aa");
        user.setBirthday(LocalDate.now());
        user.setEmail("aa@ya.ru");
        user.setLogin("aa");
    }

    @Test
    void createEmptyName() throws ValidationException {
        user.setName("");
        userController.saveUser(user);
        assertEquals(user.getLogin(), user.getName());
        assertEquals(user.getLogin(), userController.getUsers().get(0).getName());
    }

    @Test
    void updateUserNameEmpty() throws ValidationException {
        userController.saveUser(user);
        user.setName("");
        user.setLogin("bb");
        Set<ConstraintViolation<User>> violations2 = validator.validate(user);
        if (!violations2.isEmpty()) {
            userController.update(user);
        }
        assertEquals(user.getLogin(), user.getName());
        assertEquals(user.getLogin(), userController.getUsers().get(0).getName());//из листа
    }

    @Test
    void createEmptyLogin() throws ValidationException {
        user.setLogin("");
        userController.saveUser(user);
        assertEquals(user.getLogin(), userController.getUsers().get(0).getLogin(), "rep");
    }
}