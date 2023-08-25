package ru.practicum.main.request.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ParticipationRequestDto> create(@PathVariable Long userId, @RequestParam Long eventId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(requestService.create(userId, eventId));
    }

    @GetMapping
    public ResponseEntity<List<ParticipationRequestDto>> getRequestsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(requestService.getRequestsByUserId(userId));
    }

    @PatchMapping("/{requestId}/cancel")
    public ResponseEntity<ParticipationRequestDto> cancel(@PathVariable Long userId, @PathVariable Long requestId) {
        return ResponseEntity.ok(requestService.cancel(userId, requestId));
    }
}
