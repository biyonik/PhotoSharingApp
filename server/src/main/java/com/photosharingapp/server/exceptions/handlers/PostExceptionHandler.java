package com.photosharingapp.server.exceptions.handlers;

import com.photosharingapp.server.exceptions.concrete.post.*;
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
public class PostExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PostNotFoundException.class)
    public InternalApiResponse<String> handlePostNotFoundException(PostNotFoundException exception) {
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
    @ExceptionHandler(PostNotCreatedException.class)
    public InternalApiResponse<String> handlePostNotCreatedException(PostNotCreatedException exception) {
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
    @ExceptionHandler(PostNotChangeActiveStatusException.class)
    public InternalApiResponse<String> handlePostNotChangeActiveStatusException(PostNotChangeActiveStatusException exception) {
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
    @ExceptionHandler(PostNotUpdatedException.class)
    public InternalApiResponse<String> handlePostNotUpdatedException(PostNotUpdatedException exception) {
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
    @ExceptionHandler(PostNotDeletedException.class)
    public InternalApiResponse<String> handlePostNotDeletedException(PostNotDeletedException exception) {
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
    @ExceptionHandler(PostNotFileUploadException.class)
    public InternalApiResponse<String> handlePostNotFileUploadException(PostNotFileUploadException exception) {
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
    @ExceptionHandler(PostNotLikedException.class)
    public InternalApiResponse<String> handlePostNotLikedException(PostNotLikedException exception) {
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
    @ExceptionHandler(PostNotDislikedException.class)
    public InternalApiResponse<String> handlePostNotDislikedException(PostNotDislikedException exception) {
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


