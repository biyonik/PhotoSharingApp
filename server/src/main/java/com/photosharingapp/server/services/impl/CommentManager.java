package com.photosharingapp.server.services.impl;

import com.photosharingapp.server.enums.Language;
import com.photosharingapp.server.exceptions.concrete.comment.CommentNotCreatedException;
import com.photosharingapp.server.exceptions.concrete.comment.CommentNotDeletedException;
import com.photosharingapp.server.exceptions.concrete.comment.CommentNotFoundException;
import com.photosharingapp.server.exceptions.concrete.comment.CommentNotUpdatedException;
import com.photosharingapp.server.exceptions.enums.concrete.FriendlyMessageCodes;
import com.photosharingapp.server.models.Comment;
import com.photosharingapp.server.repositories.ICommentRepository;
import com.photosharingapp.server.requests.comment.CreateCommentRequest;
import com.photosharingapp.server.requests.comment.UpdateCommentRequest;
import com.photosharingapp.server.services.ICommentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CommentManager implements ICommentService {
    @Autowired
    private final ICommentRepository commentRepository;

    @Override
    public Comment create(Language language, CreateCommentRequest createCommentRequest) {
        log.debug("[{}][createComment] request: {}", this.getClass().getSimpleName(), createCommentRequest);
        try {
            Comment comment = Comment.builder()
                    .content(createCommentRequest.getContent())
                    .user(createCommentRequest.getAppUser())
                    .post(createCommentRequest.getPost())
                    .createdAt(new Date())
                    .isActive(true)
                    .build();
            Comment response = commentRepository.save(comment);
            log.debug("[{}][createComment] response: {}", this.getClass().getSimpleName(), response);
            return response;
        } catch (Exception ex) {
            throw new CommentNotCreatedException(language, FriendlyMessageCodes.COMMENT_CREATED_FAILED, "Post creation failed: " + createCommentRequest);
        }
    }

    @Override
    public Comment update(Language language, UUID id, UpdateCommentRequest updateCommentRequest) {
        log.debug("[{}][updateComment] -> request: {}", this.getClass().getSimpleName(), updateCommentRequest);
        try {
            Comment comment = getById(language, id);
            comment.setContent(updateCommentRequest.getContent());
            comment.setUpdatedAt(new Date());
            Comment commentResponse = commentRepository.save(comment);
            log.debug("[{}][updateComment] -> response: {}", this.getClass().getSimpleName(), commentResponse);
            return commentResponse;
        } catch (Exception exception) {
            throw new CommentNotUpdatedException(language, FriendlyMessageCodes.COMMENT_UPDATED_FAILED, "comment request: " + updateCommentRequest.toString());
        }
    }

    @Override
    public boolean delete(Language language, UUID id) {
        log.debug("[{}][deleteComment] -> request id: {}", this.getClass().getSimpleName(), id);
        try {
            Comment comment = getById(language, id);
            comment.setDeleted(true);
            comment.setUpdatedAt(new Date());
            commentRepository.save(comment);
            log.debug("[{}][deleteComment] -> response id: {}", this.getClass().getSimpleName(), id);
            return true;
        } catch (Exception e) {
            throw new CommentNotDeletedException(language, FriendlyMessageCodes.COMMENT_DELETE_FAILED, "Comment delete failed at id: " + id);
        }
    }

    @Override
    public Comment getById(Language language, UUID id) {
        log.debug("[{}][getById] -> request for id: {} ", this.getClass().getSimpleName(), id);
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isEmpty()) {
            throw new CommentNotFoundException(language, FriendlyMessageCodes.COMMENT_NOT_FOUND, "Comment not found for id: " + id);
        }
        log.debug("[{}][getById] -> response for id: {} ", this.getClass().getSimpleName(), comment);
        return comment.get();
    }

    @Override
    public List<Comment> getByPostId(Language language, UUID postId) {
        log.debug("[{}][getByPostId] -> request for postId: {} ", this.getClass().getSimpleName(), postId);
        List<Comment> comments = commentRepository.findByPostId(postId);
        if (comments.isEmpty()) {
            throw new CommentNotFoundException(language, FriendlyMessageCodes.COMMENT_NOT_FOUND, "Comment not found for postId: " + postId);
        }
        log.debug("[{}][getByPostId] -> response for id: {} ", this.getClass().getSimpleName(), comments);
        return comments;
    }

    @Override
    public List<Comment> getByUserId(Language language, UUID userId) {
        log.debug("[{}][getByUserId] -> request for userId: {} ", this.getClass().getSimpleName(), userId);
        List<Comment> comments = commentRepository.findByUserId(userId);
        if (comments.isEmpty()) {
            throw new CommentNotFoundException(language, FriendlyMessageCodes.COMMENT_NOT_FOUND, "Comment not found for userId: " + userId);
        }
        log.debug("[{}][getByUserId] -> response for userId: {} ", this.getClass().getSimpleName(), comments);
        return comments;
    }

    @Override
    public List<Comment> getByPostIdAndDeletedFalseOrderByCreatedAtDesc(Language language, UUID postId) {
        log.debug("[{}][getByPostIdAndDeletedFalseOrderByCreatedAtDesc] -> request for postId: {} ", this.getClass().getSimpleName(), postId);
        List<Comment> comments = commentRepository.findByPostIdAndIsDeletedFalseOrderByCreatedAtDesc(postId);
        if (comments.isEmpty()) {
            throw new CommentNotFoundException(language, FriendlyMessageCodes.COMMENT_NOT_FOUND, "Comment not found for postId: " + postId);
        }
        log.debug("[{}][getByPostIdAndDeletedFalseOrderByCreatedAtDesc] -> response for id: {} ", this.getClass().getSimpleName(), comments);
        return comments;
    }
}
