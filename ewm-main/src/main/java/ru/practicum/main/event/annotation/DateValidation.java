package ru.practicum.main.event.annotation;

import ru.practicum.main.event.validator.EventDateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ValidEventDates.class)
@Constraint(validatedBy = EventDateValidator.class)
public @interface DateValidation {
    String message() default "EventDate must be at least 2 hours later than the current time and cannot be null";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean isAdmin() default false;
}