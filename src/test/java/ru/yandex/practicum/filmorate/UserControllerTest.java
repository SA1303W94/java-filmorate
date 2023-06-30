package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class UserControllerTest {
    private UserController userController;
    private User user;
    private static Validator validator;

    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }


    @BeforeEach
    public void start() {
        InMemoryUserStorage userStorage = new InMemoryUserStorage();
        UserService userService = new UserService(userStorage);
        userController = new UserController(userService);
        user = new User();
        user.setName("aa");
        user.setBirthday(LocalDate.now());
        user.setEmail("aa@ya.ru");
        user.setLogin("aa");
    }

    @Test
    void createEmptyName() throws ValidationException {
        user.setName("");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (violations.isEmpty()) {
            userController.update(user);
        }
        assertEquals(user.getLogin(), user.getName());
        assertEquals(user.getLogin(), userController.getById(1).getName());
    }

    @Test
    void updateUserNameEmpty() throws ValidationException {
        userController.update(user);
        user.setName("");
        user.setLogin("bb");
        Set<ConstraintViolation<User>> violations2 = validator.validate(user);
        if (violations2.isEmpty()) {
            userController.update(user);
        }
        assertEquals(user.getLogin(), user.getName());
        assertEquals(user.getLogin(), userController.getById(1).getName());
    }

    @Test
    void createEmptyLogin() throws ValidationException {
        user.setLogin("");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (violations.isEmpty()) {
            userController.create(user);
        }
        assertEquals(0, userController.getAll().size());
    }
}