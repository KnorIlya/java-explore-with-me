package ru.practicum.main.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.dto.NewEventDto;
import ru.practicum.main.event.dto.UpdateEventDto;
import ru.practicum.main.event.service.EventService;
import ru.practicum.main.flag.OnCreate;
import ru.practicum.main.flag.OnUpdate;
import ru.practicum.main.request.dto.EventRequestStatusUpdateRequestDto;
import ru.practicum.main.request.dto.EventRequestStatusUpdateResultDto;
import ru.practicum.main.request.dto.ParticipationRequestDto;
import ru.practicum.main.request.service.RequestService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/events")
public class PrivateEventController {

    private final EventService eventService;
    private final RequestService requestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EventFullDto> create(@Validated(OnCreate.class) @RequestBody NewEventDto newEventDto, @PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.create(newEventDto, userId));
    }

    @GetMapping
    public ResponseEntity<List<EventShortDto>> getAllByInitiatorId(@PathVariable Long userId,
                                                                   @RequestParam(defaultValue = "0", required = false) @PositiveOrZero Integer from,
                                                                   @RequestParam(defaultValue = "10", required = false) @Positive Integer size) {
        return ResponseEntity.ok(eventService.getAllByInitiatorId(userId, from, size));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventFullDto> getEventByIdAndInitiatorId(@PathVariable Long eventId, @PathVariable Long userId) {
        return ResponseEntity.ok(eventService.getEventByIdAndInitiatorId(eventId, userId));
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventFullDto> updateByInitiator(@Validated(OnUpdate.class) @RequestBody UpdateEventDto updatedEvent,
                                          @PathVariable Long eventId,
                                          @PathVariable Long userId) {
        return ResponseEntity.ok(eventService.updateByInitiator(updatedEvent, eventId, userId));
    }

    @PatchMapping("/{eventId}/requests")
    public ResponseEntity<EventRequestStatusUpdateResultDto> processRequestsByInitiator(@RequestBody @Valid EventRequestStatusUpdateRequestDto updateRequest,
                                                                        @PathVariable Long userId,
                                                                        @PathVariable Long eventId) {
        return ResponseEntity.ok(requestService.processRequestsByInitiator(updateRequest, userId, eventId));
    }

    @GetMapping("/{eventId}/requests")
    public ResponseEntity<List<ParticipationRequestDto>> getRequestsByInitiator(@PathVariable Long userId, @PathVariable Long eventId) {
        return ResponseEntity.ok(requestService.getRequestsByInitiator(userId, eventId));
    }
}
