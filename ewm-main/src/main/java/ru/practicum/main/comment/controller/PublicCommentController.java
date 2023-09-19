package ru.practicum.main.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.main.comment.dto.ResponseCommentDto;
import ru.practicum.main.comment.service.CommentService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Validated
@RestController
@RequestMapping("/comments/{eventId}")
@RequiredArgsConstructor
public class PublicCommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<ResponseCommentDto>> getAllCommentsForEvent(@PathVariable Long eventId,
                                                                           @RequestParam(required = false) String keyword,
                                                                           @RequestParam(required = false, defaultValue = "0") @PositiveOrZero Integer from,
                                                                           @RequestParam(required = false, defaultValue = "10") @Positive Integer size) {
        return ResponseEntity.ok(commentService.getAllCommentsForEvent(eventId, keyword, from, size));
    }

}
