package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String name;
    @Email
    @NotBlank(message = "Значение пустое или null")
    private String email;
    @NotBlank(message = "Значение пустое или null")
    @Pattern(regexp = "\\S+", message = "Значение содержит пробелы")
    private String login;
    @NotNull
    @PastOrPresent(message = "Значение не наступило")
    private LocalDate birthday;
    private final Set<Integer> friends = new HashSet<>();
}