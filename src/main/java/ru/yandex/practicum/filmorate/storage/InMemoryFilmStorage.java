package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class InMemoryFilmStorage implements InMemoryStorage<Film> {
    private final Map<Integer, Film> films = new HashMap<>();

    public List<Film> getAll() {
        log.debug("Текущее количество фильмов: {}", films.size());
        return films.values().parallelStream().collect(Collectors.toList());
    }

    private int generatorId = 0;

    private int generateId() {
        return ++generatorId;
    }

    @Override
    public Film save(Film film) {
        if (!films.containsKey(film.getId())) {
            if (film.getId() > 0) {
                throw new NotFoundException("Object is not in list");
            }
            film.setId(generateId());
            log.debug("Добавлен новый id: {}", film.getId());
        }
        log.debug("Новые данные: {} {} {}", film.getId(), film.getName(), film.getReleaseDate());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film getById(int id) {
        if (!films.containsKey(id)) {
            throw new NotFoundException("Object is not in list");
        }
        return films.get(id);
    }

    @Override
    public void delete(int id) {
        if (!films.containsKey(id)) {
            throw new NotFoundException("Object is not in list");
        }
    }
}