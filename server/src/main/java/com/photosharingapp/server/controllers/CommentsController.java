package com.photosharingapp.server.controllers;

import com.photosharingapp.server.enums.Language;
import com.photosharingapp.server.exceptions.enums.concrete.FriendlyMessageCodes;
import com.photosharingapp.server.exceptions.utils.FriendlyMessageUtils;
import com.photosharingapp.server.models.Comment;
import com.photosharingapp.server.requests.comment.CreateCommentRequest;
import com.photosharingapp.server.requests.comment.UpdateCommentRequest;
import com.photosharingapp.server.responses.FriendlyMessageResponse;
import com.photosharingapp.server.responses.InternalApiResponse;
import com.photosharingapp.server.responses.concrete.CommentResponse;
import com.photosharingapp.server.services.ICommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController()
@RequestMapping(value = "/api/1.0/comment")
@RequiredArgsConstructor
public class CommentsController {
    @Autowired
    private final ICommentService commentService;

    @GetMapping("/{language}/{postId}")
    public InternalApiResponse<List<CommentResponse>> getAll(@PathVariable("language") Language language, @PathVariable("postId") UUID postId) {
        log.debug("[{}][getAllComments]", this.getClass().getSimpleName());
        List<Comment> comments = commentService.getByPostIdAndDeletedFalseOrderByCreatedAtDesc(language, postId);
        List<CommentResponse> commentResponseList = convertCommentListToCommentResponseList(comments);
        log.debug("[{}][getAllComments] -> response: {}", this.getClass().getSimpleName(), commentResponseList);
        return InternalApiResponse.<List<CommentResponse>>builder()
                .friendlyMessageResponse(FriendlyMessageResponse.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, null))
                        .build()
                )
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(commentResponseList)
                .build();
    }

    @PostMapping(value = "/{language}")
    public InternalApiResponse<CommentResponse> create(@PathVariable("language")Language language, @RequestBody CreateCommentRequest createCommentRequest) {
        log.debug("[{}][create] -> {}", this.getClass().getSimpleName(), createCommentRequest);
        Comment comment = commentService.create(language, createCommentRequest);
        CommentResponse commentResponse = convertCommentToCommentResponse(comment);
        log.debug("[{}][create] -> {}", this.getClass().getSimpleName(), commentResponse);
        return InternalApiResponse.<CommentResponse>builder()
                .httpStatus(HttpStatus.OK)
                .friendlyMessageResponse(FriendlyMessageResponse.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.COMMENT_CREATED_SUCCESSFULLY))
                        .build()
                )
                .hasError(false)
                .payload(commentResponse)
                .build();
    }

    @PutMapping(value = "/{language}/{id}")
    public InternalApiResponse<CommentResponse> update(@PathVariable("language")Language language, @PathVariable("id") UUID id, @RequestBody UpdateCommentRequest updateCommentRequest) {
        log.debug("[{}][update] -> {}", this.getClass().getSimpleName(), updateCommentRequest);
        Comment comment = commentService.update(language, id, updateCommentRequest);
        CommentResponse commentResponse = convertCommentToCommentResponse(comment);
        log.debug("[{}][update] -> {}", this.getClass().getSimpleName(), commentResponse);
        return InternalApiResponse.<CommentResponse>builder()
                .httpStatus(HttpStatus.OK)
                .friendlyMessageResponse(FriendlyMessageResponse.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.COMMENT_UPDATED_SUCCESSFULLY))
                        .build()
                )
                .hasError(false)
                .payload(commentResponse)
                .build();
    }

    @DeleteMapping(value="/{language}/{id}")
    public InternalApiResponse<Boolean> delete(@PathVariable("language") Language language, @PathVariable("id") UUID id) {
        log.debug("[{}][delete] -> request for id: {}", this.getClass().getSimpleName(), id);
        Boolean response = commentService.delete(language, id);
        log.debug("[{}][delete] -> response: {}", this.getClass().getSimpleName(), response);
        return InternalApiResponse.<Boolean>builder()
                .friendlyMessageResponse(FriendlyMessageResponse.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.COMMENT_DELETE_SUCCESSFULLY))
                        .build()
                )
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(response)
                .build();
    }

    private static CommentResponse convertCommentToCommentResponse(@NotNull Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .user(comment.getUser())
                .post(comment.getPost())
                .createdAt(comment.getCreatedAt().getTime())
                .updatedAt(comment.getCreatedAt().getTime())
                .isDeleted(comment.isDeleted())
                .isActive(comment.isActive())
                .build();
    }

    private static List<CommentResponse> convertCommentListToCommentResponseList(List<Comment> comments) {
        return comments.stream()
                .map(arg -> CommentResponse.builder()
                        .id(arg.getId())
                        .content(arg.getContent())
                        .createdAt(arg.getCreatedAt().getTime())
                        .updatedAt(arg.getUpdatedAt().getTime())
                        .user(arg.getUser())
                        .build()
                ).collect(Collectors.toList());
    }
}
