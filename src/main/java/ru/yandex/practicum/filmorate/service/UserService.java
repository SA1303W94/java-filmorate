package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService extends AbstractService<User> {
    @Autowired
    public UserService(InMemoryUserStorage storage) {
        super(storage);
    }

    public void addFriend(int userId, int friendId) { //добавление в друзья,
        if (storage.getById(userId) == null || storage.getById(friendId) == null) {
            throw new NotFoundException("Object is not in list");
        }
        storage.getById(userId).getFriends().add(friendId);
        storage.getById(friendId).getFriends().add(userId);
        log.info("Friend successfully added");
    }

    public void removeFriend(int userId, int friendId) { //удаление из друзей
        if (storage.getById(userId) == null || storage.getById(friendId) == null) {
            throw new NotFoundException("Object is not in list");
        }
        getById(userId).getFriends().remove(friendId);
        getById(friendId).getFriends().remove(userId);
        log.info("Friend successfully removed");
    }

    public List<User> getFriends(int userId) { //вывод списка друзей
        if (storage.getById(userId) == null) {
            throw new NotFoundException("Object is not in list");
        }
        User user = getById(userId);
        log.info("Friends of user with id " + userId);
        return user.getFriends()
                .stream()
                .map(storage::getById)
                .sorted(Comparator.comparingInt(User::getId))
                .collect(Collectors.toList());
    }

    public List<User> getCommonFriends(int firstId, int second2Id) { //вывод списка общих друзей
        if (storage.getById(firstId) == null ||
                storage.getById(second2Id) == null) {
            throw new NotFoundException("Object is not in list");
        }
        List<User> firstFriends = getFriends(firstId);
        List<User> secondFriends = getFriends(second2Id);
        log.info("Common friends of users with id " + firstId + " и " + second2Id);
        return firstFriends
                .stream()
                .filter(secondFriends::contains)
                .sorted(Comparator.comparingInt(User::getId))
                .collect(Collectors.toList());
    }
}