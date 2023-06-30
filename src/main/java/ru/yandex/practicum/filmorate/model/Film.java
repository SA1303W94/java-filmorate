package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.validator.RealiseDateContraint;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Film extends Model {
    private int id;
    @NotBlank(message = "Значение пустое или null")
    private String name;
    @Size(max = 200, message = "Описание больше 200 символов")
    private String description;
    @RealiseDateContraint(message = "Релиз не раньше 28 декабря 1895 года")
    private LocalDate releaseDate;
    @Positive(message = "должно быть положительным")
    private int duration;
    private Set<Integer> likes = new HashSet<>();
}