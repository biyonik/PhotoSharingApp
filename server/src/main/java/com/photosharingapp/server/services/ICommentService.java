package com.photosharingapp.server.services;

import com.photosharingapp.server.enums.Language;
import com.photosharingapp.server.models.Comment;
import com.photosharingapp.server.requests.comment.CreateCommentRequest;
import com.photosharingapp.server.requests.comment.UpdateCommentRequest;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ICommentService {
    Comment create(Language language, CreateCommentRequest createCommentRequest);
    Comment update(Language language, UUID id, UpdateCommentRequest updateCommentRequest);
    boolean delete(Language language, UUID id);
    Comment getById(Language language, UUID id);
    List<Comment> getByPostId(Language language, UUID postId);
    List<Comment> getByUserId(Language language, UUID userId);
    List<Comment> getByPostIdAndDeletedFalseOrderByCreatedAtDesc(Language language, @Param("postId") UUID postId);
}
