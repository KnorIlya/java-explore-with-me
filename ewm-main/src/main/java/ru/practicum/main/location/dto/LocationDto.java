package ru.practicum.main.location.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationDto {

    @NotNull
    Float lat;

    @NotNull
    Float lon;

}
