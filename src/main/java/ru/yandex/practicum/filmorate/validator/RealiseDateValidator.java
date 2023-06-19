package ru.yandex.practicum.filmorate.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class RealiseDateValidator implements ConstraintValidator<RealiseDateContraint, LocalDate> {
    private final int day = 28;
    private final int month = 12;
    private final int year = 1895;
    private final LocalDate realiseDate = LocalDate.of(year, month, day);

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }
        return value.isAfter(realiseDate);
    }
}