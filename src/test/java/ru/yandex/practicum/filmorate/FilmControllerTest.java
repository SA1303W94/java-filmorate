package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import javax.validation.*;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilmControllerTest {
    private FilmController filmController;
    private UserController userController;
    private Film film;
    private static Validator validator;

    @BeforeEach
    public void start() {
        InMemoryUserStorage userStorage = new InMemoryUserStorage();
        InMemoryFilmStorage filmStorage = new InMemoryFilmStorage();
        FilmService filmService = new FilmService(filmStorage, userStorage);
        UserService userService = new UserService(userStorage);
        filmController = new FilmController(filmService);
        userController = new UserController(userService);
        film = new Film();
        film.setName("aa");
        film.setDescription("aa");
        film.setReleaseDate(LocalDate.now());
        film.setDuration(10);
    }

    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    @Test
    void createBeforeRealiseFilm() throws ValidationException {
        film.setReleaseDate(LocalDate.now().minusYears(300));
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        if (violations.isEmpty()) {
            filmController.create(film);
        }
        assertEquals(1, violations.size(), "поле пустое");
        assertEquals(0, filmController.getAll().size(), " ");
    }

    @Test
    void validateDescriptionNotCorrect() throws ValidationException {
        film.setDescription("ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff");
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        if (violations.isEmpty()) {
            filmController.create(film);
        }
        assertEquals(0, filmController.getAll().size(), "Описание больше 200 символов");
    }

    @Test
    void updateUnlimitReleasedFilm_shouldShowErrorMessage() throws ValidationException {
        film.setReleaseDate(LocalDate.now().minusYears(300));
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        if (violations.isEmpty()) {
            filmController.create(film);
        }
        assertEquals(0, filmController.getAll().size(), "Дата релиза раньше возможной");
    }
}