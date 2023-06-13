package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.*;
import java.util.Set;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private static Validator validator;

    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    @Test
    void validateNameCorrectAndEmpty() {
        User user = new User(1, "AA", "aa@mail.ru", "AA", LocalDate.now());
        Set<ConstraintViolation<User>> violations1 = validator.validate(user);
        assertEquals(0, violations1.size(), "Имя пустое");

        User user1 = new User(2, "", "aa@mail.ru", "AA", LocalDate.now());
        Set<ConstraintViolation<User>> violations = validator.validate(user1);
        assertEquals(0, violations.size(), "Имя пустое");

    }

    @Test
    void validateEmailCorrectEmptyNotCorrect() {
        User user = new User(1, "AA", "aa@mail.ru", "AA", LocalDate.now());
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(0, violations.size(), "поле пустое");

        User user1 = new User(1, "AA", "", "AA", LocalDate.now());
        Set<ConstraintViolation<User>> violations1 = validator.validate(user1);
        assertEquals(1, violations1.size(), "поле не пустое");

        User user2 = new User(1, "AA", "aa.mail.ru", "AA", LocalDate.now());
        Set<ConstraintViolation<User>> violations2 = validator.validate(user2);
        assertEquals(1, violations2.size(), "неверный email");
    }

    @Test
    void validateLoginCorrectAndEmptyAndNotCorrect() {
        User user = new User(1, "AA", "aa@mail.ru", "AA", LocalDate.now());
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(0, violations.size(), "поле пустое");

        User user1 = new User(1, "AA", "aa@mail.ru", "", LocalDate.now());
        Set<ConstraintViolation<User>> violations1 = validator.validate(user1);
        assertEquals(1, violations1.size(), "поле не пустое");
    }

    @Test
    void validateDateCorrectAndEmptyAndNotCorrect() {
        User user = new User(1, "AA", "aa@mail.ru", "AA", LocalDate.now());
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(0, violations.size(), "поле пустое");

        User user1 = new User();
        user1.setLogin("AA");
        user1.setEmail("aa@mail.ru");
        Set<ConstraintViolation<User>> violations1 = validator.validate(user1);
        assertEquals(1, violations1.size(), "поле не пустое");

        User user2 = new User(1, "AA", "aa@mail.ru", "AA", LocalDate.now().plusYears(5));
        Set<ConstraintViolation<User>> violations2 = validator.validate(user2);
        assertEquals(1, violations2.size(), "не наступило");
    }
}
