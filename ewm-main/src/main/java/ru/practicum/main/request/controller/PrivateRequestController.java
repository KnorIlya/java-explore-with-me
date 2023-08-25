package ru.practicum.main.request.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.request.dto.ParticipationRequestDto;
import ru.practicum.main.request.service.RequestService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/requests")
public class PrivateRequestController {

    private final RequestService requestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto create(@PathVariable Long userId, @RequestParam Long eventId) {
        return requestService.create(userId, eventId);
    }

    @GetMapping
    public List<ParticipationRequestDto> getRequestsByUserId(@PathVariable Long userId) {
        return requestService.getRequestsByUserId(userId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancel(@PathVariable Long userId, @PathVariable Long requestId) {
        return requestService.cancel(userId, requestId);
    }
}
