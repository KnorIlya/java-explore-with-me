package ru.practicum.main.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity<ResponseCommentDto> create(@RequestBody @Valid RequestCommentDto newComment,
                                                     @PathVariable Long userId, @PathVariable Long eventId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.create(newComment, userId, eventId));
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<ResponseCommentDto> update(@RequestBody @Valid RequestCommentDto newComment,
                                                     @PathVariable Long userId, @PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.update(newComment, userId, commentId));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<ResponseCommentDto> getByIdByUser(@PathVariable Long userId, @PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.getByIdByUser(userId, commentId));
    }

    @GetMapping
    public ResponseEntity<List<ResponseCommentDto>> getUsersComments(@PathVariable Long userId) {
        return ResponseEntity.ok(commentService.getUsersComments(userId));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteByIdByUser(@PathVariable Long userId, @PathVariable Long commentId) {
        commentService.deleteByIdByUser(userId, commentId);
        return ResponseEntity.noContent().build();
    }

}
