package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dao.FilmRepository;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping
@Slf4j
public class FilmController {
    private final FilmRepository filmRepository = new FilmRepository();

    @GetMapping("/films") //    получение всех фильмов
    public List<Film> getFilms() {
        log.debug("получение всех фильмов");
        return filmRepository.getAll(); // сделала так, чтобы  repository был private, не поняла, как сделать по-другому
    }

    @PostMapping(value = "/films") //    добавление фильма;
    @ResponseStatus(HttpStatus.CREATED)
    public Film save(@Valid @RequestBody Film film) throws ValidationException {
        log.info("Create Film: {} - Started", film);
        filmRepository.save(film);
        log.info("Create Film: {} - Finished", film);
        return film;
    }

    @PutMapping(value = "/films") //    обновление пользователя;
    public Film updateFilm(@Valid @RequestBody Film film) throws ValidationException {
        log.info("Update Film: {} - Started", film);
        filmRepository.save(film);
        log.info("Update Film: {} - Finished", film);
        return film;
    }
}