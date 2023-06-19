package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dao.UserRepository;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping
@Slf4j
public class UserController {
    private final UserRepository userRepository = new UserRepository();

    @GetMapping("/users")
    public List<User> getAll() { //    получение списка всех пользователей.
        log.debug("получение списка");
        return userRepository.getAll();
    }

    @PostMapping(value = "/users") //    создание пользователя;
    @ResponseStatus(HttpStatus.CREATED)
    public User save(@Valid @RequestBody User user) throws ValidationException {
        log.info("Create User: {} - Started", user);
        userRepository.save(user);
        log.info("Create User: {} - Finished", user);
        return user;
    }

    @PutMapping(value = "/users") //    обновление пользователя;
    public User update(@Valid @RequestBody User user) throws ValidationException {
        log.info("Update User: {} - Started", user);
        userRepository.save(user);
        log.info("Update User: {} - Finished", user);
        return user;
    }
}