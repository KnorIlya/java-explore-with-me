package ru.practicum.main.compilation.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.main.event.dto.EventShortDto;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompilationResponseDto {

    Long id;
    List<EventShortDto> events;
    Boolean pinned;
    String title;
}
