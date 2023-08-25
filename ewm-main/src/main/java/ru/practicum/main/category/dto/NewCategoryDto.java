package ru.practicum.main.category.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewCategoryDto {

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 1, max = 50, message = "The category name must be from 1 to 50 characters")
    String name;
}
