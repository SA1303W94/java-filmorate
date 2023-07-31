package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController extends Controller<User> {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        super(userService);
        this.userService = userService;
    }

    @GetMapping
    public Collection<User> getAll() {
        log.debug("получение всех пользователей");
        return userService.getAll();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info("Создание пользователя: {} - Started", user);
        userService.create(user);
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info("Обновление пользователя: {} - Started", user);
        userService.update(user);
        return user;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int userId) {
        log.info("Удаление пользователя: {} - Started", userId);
        userService.delete(userId);
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable int id) {
        log.info("Поиск пользователя по id: {} - Started", id);
        return userService.getById(id);
    }

    @PutMapping("/{id}/friends/{friendId}") // добавление в друзья.
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("пользователь {} добавляет в друзья: {} - Started", id, friendId);
        userService.addFriend(id, friendId);
        log.info("пользователь {} добавляет в друзья: {} - Finished", id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}") // удаление из друзей.
    public void removeFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("пользователь {} удаляет из друзей: {} - Started", id, friendId);
        userService.removeFriend(id, friendId);
        log.info("пользователь {} удаляет из друзей: {} - Finished", id, friendId);
    }

    @GetMapping("/{id}/friends") // возвращаем список пользователей, являющихся его друзьями.
    public List<User> getFriends(@PathVariable int id) {
        log.info("список пользователей, являющихся друзьями пользователя: {}", id);
        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}") //список друзей, общих с другим пользователем.
    public List<User> getCommonFriends(@PathVariable int id, @PathVariable int otherId) {
        log.info("список общих друзей, пользователя {} и пользователя {}", id, otherId);
        return userService.getCommonFriends(id, otherId);
    }
}