package com.photosharingapp.server.exceptions.handlers;

import com.photosharingapp.server.exceptions.concrete.comment.CommentNotCreatedException;
import com.photosharingapp.server.exceptions.concrete.comment.CommentNotDeletedException;
import com.photosharingapp.server.exceptions.concrete.comment.CommentNotFoundException;
import com.photosharingapp.server.exceptions.concrete.comment.CommentNotUpdatedException;
import com.photosharingapp.server.exceptions.enums.concrete.FriendlyMessageCodes;
import com.photosharingapp.server.exceptions.utils.FriendlyMessageUtils;
import com.photosharingapp.server.responses.FriendlyMessageResponse;
import com.photosharingapp.server.responses.InternalApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@RestControllerAdvice
public class CommentExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CommentNotFoundException.class)
    public InternalApiResponse<String> handleCommentNotFoundException(CommentNotFoundException exception) {
        return InternalApiResponse.<String>builder()
                .friendlyMessageResponse(
                        FriendlyMessageResponse.builder()
                                .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                                .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                                .build()
                ).httpStatus(HttpStatus.NOT_FOUND)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CommentNotCreatedException.class)
    public InternalApiResponse<String> handleCommentNotCreatedException(CommentNotCreatedException exception) {
        return InternalApiResponse.<String>builder()
                .friendlyMessageResponse(
                        FriendlyMessageResponse.builder()
                                .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                                .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                                .build()
                ).httpStatus(HttpStatus.BAD_REQUEST)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CommentNotUpdatedException.class)
    public InternalApiResponse<String> handleCommentNotUpdatedException(CommentNotUpdatedException exception) {
        return InternalApiResponse.<String>builder()
                .friendlyMessageResponse(
                        FriendlyMessageResponse.builder()
                                .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                                .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                                .build()
                ).httpStatus(HttpStatus.BAD_REQUEST)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CommentNotDeletedException.class)
    public InternalApiResponse<String> handleCommentNotDeletedException(CommentNotDeletedException exception) {
        return InternalApiResponse.<String>builder()
                .friendlyMessageResponse(
                        FriendlyMessageResponse.builder()
                                .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                                .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                                .build()
                ).httpStatus(HttpStatus.BAD_REQUEST)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .build();
    }
}


