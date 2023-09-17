package ru.practicum.main.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.comment.dto.RequestCommentDto;
import ru.practicum.main.comment.dto.ResponseCommentDto;
import ru.practicum.main.comment.service.CommentService;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/users/{userId}/comments")
@RequiredArgsConstructor
public class PrivateCommentController {

    private final CommentService commentService;

    @PostMapping("/{eventId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseCommentDto create(@RequestBody @Valid RequestCommentDto newComment,
                                     @PathVariable Long userId, @PathVariable Long eventId) {
        return commentService.create(newComment, userId, eventId);
    }

    @PatchMapping("/{commentId}")
    public ResponseCommentDto update(@RequestBody @Valid RequestCommentDto newComment,
                                     @PathVariable Long userId, @PathVariable Long commentId) {
        return commentService.update(newComment, userId, commentId);
    }

    @GetMapping("/{commentId}")
    public ResponseCommentDto getByIdByUser(@PathVariable Long userId, @PathVariable Long commentId) {
        return commentService.getByIdByUser(userId, commentId);
    }

    @GetMapping
    public List<ResponseCommentDto> getUsersComments(@PathVariable Long userId) {
        return commentService.getUsersComments(userId);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByIdByUser(@PathVariable Long userId, @PathVariable Long commentId) {
        commentService.deleteByIdByUser(userId, commentId);
    }

}
