package ru.yandex.practicum.filmorate.dao;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class UserRepository {
    private final Map<Integer, User> users = new HashMap<>();

    private int generatorId = 0;

    public List<User> getAll() { //    получение списка всех пользователей.
        log.debug("Текущее количество пользователей: {}", users.size());
        return users.values().parallelStream().collect(Collectors.toList());
    }

    private int generateId() {
        return ++generatorId;
    }


    public void save(User user) throws ValidationException {
        if (!users.containsKey(user.getId())) {
            if (user.getId() > 0) {
                throw new ValidationException("Object is not in list");
            }
            user.setId(generateId());
            log.debug("Добавлен новый id: {}", user.getId());
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.debug("Для пользователя с логином {} установлено новое имя {}", user.getLogin(), user.getName());
        }
        log.debug("Новые данные: {} {} {}", user.getId(), user.getName(), user.getLogin());
        users.put(user.getId(), user);
    }
}