package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.filmorate.model.Model;
import ru.yandex.practicum.filmorate.service.AbstractService;

import java.util.Collection;

public abstract class Controller<T extends Model> {
    private final AbstractService<T> service;

    protected Controller(AbstractService<T> service) {
        this.service = service;
    }

    public Collection<T> getAll() {
        return service.getAll();
    }

    public T create(T obj) {
        service.create(obj);
        return obj;
    }

    public T update(T obj) {
        service.update(obj);
        return obj;
    }

    public T getById(@PathVariable int id) {
        return service.getById(id);
    }

    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}