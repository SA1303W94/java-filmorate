package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController extends Controller<Film> {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        super(filmService);
        this.filmService = filmService;
    }

    @GetMapping //    получение всех фильмов
    public Collection<Film> getFilms() {
        log.debug("получение всех фильмов");
        return filmService.getAll();
    }

    @PostMapping //    добавление фильма;
    @ResponseStatus(HttpStatus.CREATED)
    public Film create(@Valid @RequestBody Film film) {
        log.info("Create Film: {} - Started", film);
        filmService.create(film);
        log.info("Create Film: {} - Finished", film);
        return film;
    }

    @PutMapping //    обновление пользователя;
    public Film update(@Valid @RequestBody Film film) {
        log.info("Update Film: {} - Started", film);
        filmService.update(film);
        log.info("Update Film: {} - Finished", film);
        return film;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        log.info("delete Film: {}.", id);
        filmService.delete(id);
    }

    @GetMapping("/{id}")
    public Film getById(@PathVariable int id) {
        log.info("Поиск фильма с id: {} - Started", id);
        return filmService.getById(id);
    }

    @PutMapping("/{id}/like/{userId}") //пользователь ставит лайк фильму.
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        log.info("пользователь {} ставит лайк фильму: {} - Started", userId, id);
        filmService.addLike(id, userId);
        log.info("\"пользователь {} ставит лайк фильму: {} - Finished", userId, id);
    }

    @DeleteMapping("/{id}/like/{userId}") //пользователь удаляет лайк.
    public void removeLike(@PathVariable int id, @PathVariable int userId) {
        log.info("пользователь {} удаляет лайк фильма: {} - Started", userId, id);
        filmService.removeLike(id, userId);
        log.info("\"пользователь {} удаляет лайк фильма: {} - Finished", userId, id);
    }

    @GetMapping("/popular") // список из первых count (10 по умолчанию) фильмов по количеству лайков.
    public List<Film> getPopular(@RequestParam(defaultValue = "10") int count) {
        log.info("первые {} фильмов по популярности.", count);
        return filmService.getPopular(count);
    }
}