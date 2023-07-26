package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class UserService extends AbstractService<User> {
    private static final Comparator<User> USER_ID_COMPARATOR = Comparator.comparingInt(User::getId);

    private final UserStorage storage;

    @Autowired
    public UserService(UserStorage storage) {
        super(storage);
        this.storage = storage;
    }


    @Override
    public User create(User user) {
        checkName(user);
        log.debug("Новые данные: {} {} {}", user.getId(), user.getName(), user.getLogin());
        storage.create(user);
        return user;
    }

    @Override
    public User update(User user) {
        checkName(user);
        log.debug("Новые данные: {} {} {}", user.getId(), user.getName(), user.getLogin());
        storage.update(user);
        return user;
    }

    public void checkName(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.debug("Для пользователя с логином {} установлено новое имя {}", user.getLogin(), user.getName());
        }
    }

    private void isEmpty(int userId, int friendId) {
        if (storage.getById(friendId) == null || storage.getById(userId) == null) {
            throw new NotFoundException("Object is not in list");
        }
    }

    public void addFriend(int userId, int friendId) { //добавление в друзья кто , кого
        isEmpty(userId, friendId);
        storage.addFriend(userId, friendId);
        log.info("Friend successfully added");
    }

    public void removeFriend(int userId, int friendId) { //удаление из друзей
        isEmpty(userId, friendId);
        storage.removeFriend(userId, friendId);
        log.info("Friend successfully removed");
    }

    public List<User> getFriends(int userId) { //вывод списка друзей
        isEmpty(userId,userId);
        List<User> friends = storage.getFriends(userId);
        log.info("Friends of user with ID = " + userId + friends);
        return friends;
    }

    public List<User> getCommonFriends(int firstId, int second2Id) { //вывод списка общих друзей
        isEmpty(firstId, second2Id);
        log.info("Common friends of users with id " + firstId + " и " + second2Id);
    List<User> commonFriends = storage.getCommonFriends(firstId, second2Id);
        log.info("Common friends of users with ID " + " {} and {} {} ", firstId, second2Id, commonFriends);
        return commonFriends;
}
}