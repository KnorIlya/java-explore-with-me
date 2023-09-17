package ru.practicum.main.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.main.comment.dto.ResponseCommentDto;
import ru.practicum.main.comment.service.CommentService;

@RestController
@RequestMapping("/admin/comments")
@RequiredArgsConstructor
public class AdminCommentController {

    private final CommentService commentService;

    @GetMapping("/{commentId}")
    public ResponseEntity<ResponseCommentDto> getByIdByAdmin(@PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.getByIdByAdmin(commentId));
    }


    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteByIdByAdmin(@PathVariable Long commentId) {
        commentService.deleteByIdByAdmin(commentId);
        return ResponseEntity.noContent().build();
    }

}
