package ru.practicum.main.user.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {

    Long id;

    @Size(min = 2, max = 250, message = "Name must be from 2 to 250 characters")
    @NotBlank(message = "Name cannot be blank")
    String name;

    @Email(message = "Incorrect email")
    @NotBlank(message = "Incorrect email")
    @Size(min = 6, max = 254, message = "Email length must be between 6 and 254 characters")
    String email;
}
