package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Model;

import java.util.*;

@Slf4j
@Component
public abstract class AbstractStorage<T extends Model> implements InMemoryStorage<T> {
    protected final Map<Integer, T> entities = new HashMap<>();
    private int id = 0;

    @Override
    public List<T> getAll() {
        log.info("Общее количество: " + entities.size());
        return new ArrayList<>(entities.values());
    }

    @Override
    public T save(T obj) {
        if (!entities.containsKey(obj.getId())) {
            if (obj.getId() > 0) {
                throw new NotFoundException("Object is not in list");
            }
            obj.setId(++id);
            log.debug("Добавлен новый id: {}", obj.getId());
        }
        log.debug("Новые данные: {} {}", obj.getId(), obj);
        entities.put(obj.getId(), obj);
        return obj;
    }

    @Override
    public T getById(int id) {
        if (!entities.containsKey(id)) {
            throw new NotFoundException("Object is not in list");
        }
        return entities.get(id);
    }

    @Override
    public void delete(int id) {
        if (!entities.containsKey(id)) {
            throw new NotFoundException("Object is not in list");
        }
        entities.remove(id);
    }
}