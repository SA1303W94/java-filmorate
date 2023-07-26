package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Model;

import java.util.List;

public interface InMemoryStorage<T extends Model> {
    List<T> getAll();

    T create(T obj);

    T update(T obj);

    T getById(int id);

    void delete(int id);
}