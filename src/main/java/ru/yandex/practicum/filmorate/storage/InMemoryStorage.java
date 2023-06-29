package ru.yandex.practicum.filmorate.storage;

import java.util.List;

public interface InMemoryStorage<T> {
    List<T> getAll();

    T save(T obj);

    T getById(int id);

    void delete(int id);
}