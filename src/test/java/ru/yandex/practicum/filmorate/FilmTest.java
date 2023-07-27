package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilmTest {
    private static Validator validator;
    private Film film;

    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }


    @BeforeEach
    void createFilm() {
        film = new Film(1, "aa", "aa", LocalDate.now(), 10, null,null);
    }

    @Test
    void validateNameCorrect() {
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(0, violations.size(), "поле пустое");
    }

    @Test
    void validateNameEmpty() {
        film.setName("");
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size(), "поле не пустое");
    }

    @Test
    void validateDescriptionCorrect() {
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(0, violations.size(), "поле пустое");
    }

    @Test
    void validateDescriptionNotCorrect() {
        film.setDescription("ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff");
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size(), "поле больше 200 символов");
    }

    @Test
    void validateDescriptionEmpty() {
        film.setDescription("");
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(0, violations.size(), "поле не пустое");
    }

    @Test
    void validateRealiseCorrect() {
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(0, violations.size(), "поле пустое");
    }

    @Test
    void validateRealiseNotCorrect() {
        film.setReleaseDate(LocalDate.now().minusYears(300));
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size(), "поле ранее 1895 года");
    }

    @Test
    void validateRealiseEmpty() {
        film = new Film();
        film.setName("aa");
        film.setDescription("aa");
        film.setDuration(10);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(0, violations.size(), "поле не пустое");
    }

    @Test
    void validateDurationCorrect() {
        film.setDuration(10);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(0, violations.size(), "поле пустое");
    }

    @Test
    void validateDurationNotCorrect() {
        film.setDuration(-10);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size(), "поле отрицательное");
    }

    @Test
    void validateDurationNotCorrectNull() {
        film.setDuration(0);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size(), "поле отрицательное");
    }

    @Test
    void validateDurationEmpty() {
        Film film = new Film();
        film.setName("aa");
        film.setDescription("aa");
        film.setReleaseDate(LocalDate.now());
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size(), "поле не пустое");
    }
}