package ru.practicum.main.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.comment.dto.RequestCommentDto;
import ru.practicum.main.comment.dto.ResponseCommentDto;
import ru.practicum.main.comment.mapper.CommentMapper;
import ru.practicum.main.comment.model.Comment;
import ru.practicum.main.comment.repository.CommentRepository;
import ru.practicum.main.event.enums.EventState;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.repository.EventRepository;
import ru.practicum.main.exception.EntityNotFoundException;
import ru.practicum.main.exception.ForbiddenException;
import ru.practicum.main.user.model.User;
import ru.practicum.main.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CommentMapper commentMapper;

    @Transactional
    public ResponseCommentDto create(RequestCommentDto newComment, Long userId, Long eventId) {
        User user = checkIfUserExistsAndGet(userId);
        Event event = checkIfEventExistsAndGet(eventId);
        checkIfPublished(event);

        Comment comment = commentRepository.save(commentMapper.toComment(newComment, user, event));
        return commentMapper.toResponseCommentDto(comment);
    }

    @Transactional
    public ResponseCommentDto update(RequestCommentDto newComment, Long userId, Long commentId) {

        checkIfUserExists(userId);
        Comment comment = checkIfOwnCommentExistsAndGet(userId, commentId);
        comment.setMessage(newComment.getMessage());

        return commentMapper.toResponseCommentDto(commentRepository.save(comment));
    }

    @Transactional(readOnly = true)
    public ResponseCommentDto getByIdByUser(Long userId, Long commentId) {
        return commentMapper.toResponseCommentDto(checkIfOwnCommentExistsAndGet(userId, commentId));
    }

    @Transactional(readOnly = true)
    public List<ResponseCommentDto> getUsersComments(Long userId) {
        return commentMapper.toResponseCommentDto(commentRepository.getCommentsByAuthorId(userId));
    }

    @Transactional(readOnly = true)
    public ResponseCommentDto getByIdByAdmin(Long commentId) {
        return commentMapper.toResponseCommentDto(checkIfCommentExistsAndGet(commentId));
    }


    @Transactional
    public void deleteByIdByAdmin(Long commentId) {
        checkIfCommentExists(commentId);
        commentRepository.deleteById(commentId);
    }

    @Transactional(readOnly = true)
    public List<ResponseCommentDto> getAllCommentsForEvent(Long eventId, String keyword, Integer from, Integer size) {
        return commentMapper.toResponseCommentDto(commentRepository
                .findAllCommentsForEvent(eventId, keyword, from, size));
    }

    @Transactional
    public void deleteByIdByUser(Long userId, Long commentId) {
        checkIfOwnCommentExistsAndGet(userId, commentId);
        commentRepository.deleteById(commentId);
    }

    private Comment checkIfOwnCommentExistsAndGet(Long userId, Long commentId) {
        Comment comment = checkIfCommentExistsAndGet(commentId);
        if (!comment.getAuthor().getId().equals(userId)) {
            throw new ForbiddenException("Cannot operate with not own comment");
        }
        return comment;
    }

    private Comment checkIfCommentExistsAndGet(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException(Comment.class, commentId));
    }

    private void checkIfCommentExists(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new EntityNotFoundException(Comment.class, commentId);
        }
    }

    private User checkIfUserExistsAndGet(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(User.class, userId));
    }

    private Event checkIfEventExistsAndGet(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException(Event.class, eventId));
    }

    private void checkIfUserExists(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException(User.class, userId);
        }
    }

    private void checkIfPublished(Event event) {
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ForbiddenException("Cannot create a comment for an unpublished event");
        }
    }

}
