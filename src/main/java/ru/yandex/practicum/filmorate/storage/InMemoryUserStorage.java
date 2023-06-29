package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class InMemoryUserStorage implements InMemoryStorage<User> {
    private final Map<Integer, User> users = new HashMap<>();

    private int generatorId = 0;

    public List<User> getAll() { //    получение списка всех пользователей.
        log.debug("Текущее количество пользователей: {}", users.size());
        return users.values().parallelStream().collect(Collectors.toList());
    }

    private int generateId() {
        return ++generatorId;
    }

    @Override
    public User save(User user) {
        if (!users.containsKey(user.getId())) {
            if (user.getId() > 0) {
                throw new NotFoundException("Object is not in list");
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
        return user;
    }

    public User getById(int id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException("Object is not in list");
        }
        return users.get(id);
    }

    @Override
    public void delete(int id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException("Object is not in list");
        }
        users.remove(id);
    }

    public void addFriend(int userId, int friendId) {
        if (!users.containsKey(userId) || !users.containsKey(friendId)) {
            throw new NotFoundException("Object is not in list");
        }
        users.get(userId).getFriends().add(friendId);
    }
}