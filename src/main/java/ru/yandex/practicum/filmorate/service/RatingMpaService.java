package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.db.RatingMpaDb;

import java.util.List;

@Service
public class RatingMpaService {
    private final RatingMpaDb ratingMpaDbStorage;

    public RatingMpaService(RatingMpaDb ratingMpaDbStorage) {
        this.ratingMpaDbStorage = ratingMpaDbStorage;
    }

    public Mpa getRatingMpaById(int id) {
        Mpa mpa = ratingMpaDbStorage.getRatingMpaById(id);
        if (mpa == null) {
            throw new NotFoundException("Rating not found");
        }
        return mpa;
    }

    public List<Mpa> getRatingsMpa() {
        return ratingMpaDbStorage.getRatingsMpa();
    }
}