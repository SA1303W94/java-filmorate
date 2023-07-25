package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

@Slf4j
@Component
public class InMemoryUserStorage extends AbstractStorage<User> implements InMemoryStorage<User> {
    private int generatorId = 0;

    private int generateId() {
        return ++generatorId;
    }

    @Override
    public User save(User user) {
        if (!entities.containsKey(user.getId())) {
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
        entities.put(user.getId(), user);
        return user;
    }
}