package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.db.GenreDb;

import java.util.List;

@Service
public class GenreService {
    private final GenreDb genreDbStorage;

    public GenreService(GenreDb genreDbStorage) {
        this.genreDbStorage = genreDbStorage;
    }

    public Genre getGenreById(int id) {
        Genre genre = genreDbStorage.getById(id);
        if (genre == null) {
            throw new NotFoundException("Genre not found");
        }
        return genre;
    }

    public List<Genre> getAllGenres() {
        return genreDbStorage.getAll();
    }
}