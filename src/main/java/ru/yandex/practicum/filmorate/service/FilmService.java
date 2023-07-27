package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FilmService extends AbstractService<Film> {
    private final UserStorage userStorage;
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        super(filmStorage);
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    private void isEmpty(int filmId, int userId) {
        if (storage.getById(filmId) == null || userStorage.getById(userId) == null) {
            throw new NotFoundException("Object is not in list");
        }
    }

    public void addLike(int filmId, int userId) { //добавление лайка
        isEmpty(filmId, userId);
        filmStorage.addLike(filmId, userId);
        log.info("Like successfully added");
    }

    public void removeLike(int filmId, int userId) { // удаление лайка
        isEmpty(filmId, userId);
        filmStorage.removeLike(filmId, userId);
        log.info("Like successfully removed");
    }

    public List<Film> getPopular(int count) { // вывод 10 наиболее популярных фильмов по количеству лайков
        List<Film> popularFilms = new ArrayList<>(filmStorage.getPopular(count));
        log.info("Requested a list of popular movies");
        return popularFilms;
    }
}