package ru.practicum.main.event.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.practicum.main.event.enums.EventStateAction;

@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateEventDto extends NewEventDto {

    EventStateAction stateAction;
}
