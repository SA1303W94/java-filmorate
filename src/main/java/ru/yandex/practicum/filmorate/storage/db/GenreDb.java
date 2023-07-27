package ru.yandex.practicum.filmorate.storage.db;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.ArrayList;
import java.util.List;

@Repository
public class GenreDb implements Storage<Genre> { // Storage - общий интерфейс для фильмов,
    // пользователей и жанров
    private final JdbcTemplate jdbcTemplate;

    public GenreDb(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void delete(int filmId) {
        String sglQuery = "DELETE film_genres WHERE genre_id = ?";
        jdbcTemplate.update(sglQuery, filmId);
    }

    @Override
    public Genre getById(int genreId) {
        String sql = "SELECT * FROM genres WHERE genre_id = ?";
        SqlRowSet srs = jdbcTemplate.queryForRowSet(sql, genreId);
        if (srs.next()) {
            return new Genre(genreId, srs.getString("genre_name"));
        } else {
            throw new NotFoundException("Genre with ID=" + genreId + " not found!");
        }
    }

    @Override
    public List<Genre> getAll() {
        List<Genre> genres = new ArrayList<>();
        String sql = "SELECT * FROM genres ";
        SqlRowSet srs = jdbcTemplate.queryForRowSet(sql);
        while (srs.next()) {
            genres.add(new Genre(srs.getInt("genre_id"), srs.getString("genre_name")));
        }
        return genres;
    }

    @Override
    public Genre create(Genre obj) {
        return null;
    }

    @Override
    public Genre update(Genre obj) {
        return null;
    }
}