package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dao.FilmRepository;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
@Validated
@Slf4j
public class FilmController {
    private final FilmRepository repository = new FilmRepository();

    @GetMapping("/films") //    получение всех фильмов
    public List<Film> getFilms() {
        log.debug("Текущее количество фильмов: {}", repository.films.size());
        return repository.films.values().parallelStream().collect(Collectors.toList());
    }

    @PostMapping(value = "/films") //    добавление фильма;
    @ResponseStatus(HttpStatus.CREATED)
    public Film saveFilm(@Valid @RequestBody Film film) throws ValidationException {
        log.info("Create Film: {} - Started", film);
        repository.save(film);
        log.info("Create Film: {} - Finished", film);
        return film;
    }

    @PutMapping(value = "/films") //    обновление пользователя;
    public Film updateFilm(@Valid @RequestBody Film film) throws ValidationException {
        log.info("Update Film: {} - Started", film);
        repository.save(film);
        log.info("Update Film: {} - Finished", film);
        return film;
    }
}