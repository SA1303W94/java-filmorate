package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.*;
import java.util.Set;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private static Validator validator;
    User user;

    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    @BeforeEach
    void createUser() {
        user = new User();
        user.setLogin("aa");
        user.setName("aa");
        user.setEmail("aa@ya.ru");
        user.setBirthday(LocalDate.now().minusYears(20));
    }

    @Test
    void validateNameCorrect() {
        Set<ConstraintViolation<User>> violations1 = validator.validate(user);
        assertEquals(0, violations1.size(), "Имя пустое");
    }

    @Test
    void validateNameEmpty() {
        user.setName("");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(0, violations.size(), "Имя пустое");
    }

    @Test
    void validateEmailCorrect() {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(0, violations.size(), "поле пустое");
    }

    @Test
    void validateEmailEmpty() {
        user.setEmail("");
        Set<ConstraintViolation<User>> violations1 = validator.validate(user);
        assertEquals(1, violations1.size(), "поле не пустое");
    }

    @Test
    void validateEmailNotCorrect() {
        user.setEmail("aa.mail.ru");
        Set<ConstraintViolation<User>> violations2 = validator.validate(user);
        assertEquals(1, violations2.size(), "неверный email");
    }

    @Test
    void validateLoginCorrect() {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(0, violations.size(), "поле пустое");
    }

    @Test
    void validateLoginEmpty() {
        user.setLogin("");
        Set<ConstraintViolation<User>> violations1 = validator.validate(user);
        assertEquals(1, violations1.size(), "поле не пустое");
    }

    @Test
    void validateDateCorrect() {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(0, violations.size(), "поле пустое");
    }

    @Test
    void validateDateEmpty() {
        User user1 = new User();
        user1.setLogin("AA");
        user1.setEmail("aa@mail.ru");
        Set<ConstraintViolation<User>> violations1 = validator.validate(user1);
        assertEquals(1, violations1.size(), "поле не пустое");
    }

    @Test
    void validateDateNotCorrect() {
        user.setBirthday(LocalDate.now().plusYears(5));
        Set<ConstraintViolation<User>> violations2 = validator.validate(user);
        assertEquals(1, violations2.size(), "не наступило");
    }
}