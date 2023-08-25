package ru.practicum.main.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.main.request.model.RequestState;

import java.time.LocalDateTime;

import static ru.practicum.main.DatePatterns.DATE_PATTERN;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ParticipationRequestDto {

    @JsonFormat(pattern = DATE_PATTERN)
    LocalDateTime created;

    Long event;
    Long id;
    Long requester;
    RequestState status;
}
