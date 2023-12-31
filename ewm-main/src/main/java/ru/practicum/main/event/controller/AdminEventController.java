package ru.practicum.main.event.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.UpdateEventDto;
import ru.practicum.main.event.enums.EventState;
import ru.practicum.main.event.service.EventService;
import ru.practicum.main.flag.OnUpdateAdmin;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.main.DatePatterns.DATE_PATTERN;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/events")
public class AdminEventController {

    private final EventService eventService;

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventFullDto> updateByAdmin(@RequestBody @Validated(OnUpdateAdmin.class) UpdateEventDto updatedEvent,
                                                      @PathVariable Long eventId) {
        return ResponseEntity.ok(eventService.updateByAdmin(updatedEvent, eventId));
    }

    @GetMapping
    public ResponseEntity<List<EventFullDto>> getAllEventsByAdmin(@RequestParam(required = false) List<Long> users,
                                                                  @RequestParam(required = false) List<EventState> states,
                                                                  @RequestParam(required = false) List<Long> categories,
                                                                  @RequestParam(required = false) @DateTimeFormat(pattern = DATE_PATTERN) LocalDateTime rangeStart,
                                                                  @RequestParam(required = false) @DateTimeFormat(pattern = DATE_PATTERN) LocalDateTime rangeEnd,
                                                                  @RequestParam(required = false, defaultValue = "0") @PositiveOrZero Integer from,
                                                                  @RequestParam(required = false, defaultValue = "10") @Positive Integer size) {
        return ResponseEntity.ok(eventService.getAllEventsByAdmin(
                users,
                states,
                categories,
                rangeStart,
                rangeEnd,
                from,
                size
        ));
    }
}
