package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.*;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilmControllerTest {
    private FilmController filmController;
    private Film film;
    private static Validator validator;

    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    @BeforeEach
    public void start() {
        filmController = new FilmController();
        film = new Film();
        film.setName("aa");
        film.setDescription("aa");
        film.setReleaseDate(LocalDate.now());
        film.setDuration(10);
    }

    @Test
    void createBeforeRealiseFilm() throws ValidationException {
        film.setReleaseDate(LocalDate.now().minusYears(300));
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        if (violations.isEmpty()) {
            filmController.save(film);
        }
        assertEquals(1, violations.size(), "поле пустое");
        assertEquals(0, filmController.getFilms().size(), " ");
    }

    @Test
    void validateDescriptionNotCorrect() throws ValidationException {
        film.setDescription("ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff");
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        if (violations.isEmpty()) {
            filmController.save(film);
        }
        assertEquals(0, filmController.getFilms().size(), " ");
    }

    @Test
    void updateUnlimitReleasedFilm_shouldShowErrorMessage() throws ValidationException {
        film.setReleaseDate(LocalDate.now().minusYears(300));
        filmController.save(film);
    }
}

