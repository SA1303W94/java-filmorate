package ru.yandex.practicum.filmorate.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class FilmRepository {
    public Map<Integer, Film> films = new HashMap<>();
    private int generatorId = 0;

    public int generateId() {
        return ++generatorId;
    }

    public void save(Film film) throws ValidationException {
        if (!films.containsKey(film.getId())) {
            if (film.getId() > 0) {
                throw new ValidationException("Object is not in list");
            }
            film.setId(generateId());
            log.debug("Добавлен новый id: {}", film.getId());
        }
        log.debug("Новые данные: {} {} {}", film.getId(), film.getName(), film.getReleaseDate());
        films.put(film.getId(), film);
    }
}