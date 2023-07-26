package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Service
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mpa {
    @Positive
    private int id;
    @NotBlank
    private String name;
}