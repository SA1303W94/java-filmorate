package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Model;
import ru.yandex.practicum.filmorate.storage.InMemoryStorage;

import java.util.Collection;

@Slf4j
@Service
public abstract class AbstractService<T extends Model> {
    protected InMemoryStorage<T> storage;

    protected AbstractService(InMemoryStorage<T> storage) {
        this.storage = storage;
    }

    public Collection<T> getAll() {
        log.info("List of all objects: " + storage.getAll().size());
        return storage.getAll();
    }

    public T create(T obj) {
        log.info("Object successfully added: " + obj);
        return storage.save(obj);
    }

    public T update(T obj) {
        log.info("Object successfully added: " + obj);
        return storage.save(obj);
    }

    public T getById(int id) {
        log.info("Requested object with id: " + id);
        return storage.getById(id);
    }

    public void delete(int id) {
        log.info("Deleted object with id: {}", id);
        storage.delete(id);
    }
}