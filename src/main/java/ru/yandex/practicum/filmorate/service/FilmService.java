package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService extends AbstractService<Film> {
    private final InMemoryUserStorage userStorage;

    @Autowired
    public FilmService(InMemoryFilmStorage filmStorage, InMemoryUserStorage userStorage) {
        super(filmStorage);
        this.userStorage = userStorage;
    }

    public void addLike(int filmId, int userId) { //добавление лайка
        if (storage.getById(filmId) == null || userStorage.getById(userId) == null) {
            throw new NotFoundException("Object is not in list");
        }
        getById(filmId).getLikes().add(userId);
        log.info("Like successfully added");
    }

    public void removeLike(int filmId, int userId) { // удаление лайка
        if (storage.getById(filmId) == null || userStorage.getById(userId) == null) {
            throw new NotFoundException("Object is not in list");
        }
        getById(filmId).getLikes().remove(userId);
        log.info("Like successfully removed");
    }

    public List<Film> getPopular(int count) { // вывод 10 наиболее популярных фильмов по количеству лайков
        log.info("Requested a list of popular movies");
        return storage.getAll()
                .stream()
                .sorted(Comparator.comparing(f -> f.getLikes().size(), Comparator.reverseOrder()))
                .limit(count)
                .collect(Collectors.toList());
    }
}