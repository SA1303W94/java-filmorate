package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.db.GenreDb;

@Service
public class GenreService extends AbstractService<Genre> {
    private final GenreDb genreDbStorage;

    public GenreService(GenreDb genreDbStorage) {
        super(genreDbStorage);
        this.genreDbStorage = genreDbStorage;
    }
}