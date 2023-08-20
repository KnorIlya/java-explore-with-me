package ru.practicum.main.request.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.main.request.model.RequestProcessedState;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventRequestStatusUpdateRequestDto {

    @NotNull
    @NotEmpty
    List<Long> requestIds;

    @NotNull
    RequestProcessedState status;
}
