package com.photosharingapp.server.exceptions.handlers;

import com.photosharingapp.server.exceptions.concrete.appuser.*;
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
public class AppUserExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AppUserNotFoundException.class)
    public InternalApiResponse<String> handleAppUserNotFoundException(AppUserNotFoundException exception) {
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
    @ExceptionHandler(AppUserNotCreatedException.class)
    public InternalApiResponse<String> handleAppUserNotCreatedException(AppUserNotCreatedException exception) {
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
    @ExceptionHandler(AppUserNotChangePasswordsException.class)
    public InternalApiResponse<String> handleAppUserNotChangePasswordsException(AppUserNotChangePasswordsException exception) {
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
    @ExceptionHandler(AppUserNotChangeActiveStatusException.class)
    public InternalApiResponse<String> handleAppUserNotChangeActiveStatusException(AppUserNotChangeActiveStatusException exception) {
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
    @ExceptionHandler(AppUserNotUpdatedException.class)
    public InternalApiResponse<String> handleAppUserNotUpdatedException(AppUserNotUpdatedException exception) {
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
    @ExceptionHandler(AppUserNotDeletedException.class)
    public InternalApiResponse<String> handleAppUserNotDeletedException(AppUserNotDeletedException exception) {
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

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(AppUserEmailAreadyExistsException.class)
    public InternalApiResponse<String> handleAppUserEmailAreadyExistsException(AppUserEmailAreadyExistsException exception) {
        return InternalApiResponse.<String>builder()
                .friendlyMessageResponse(
                        FriendlyMessageResponse.builder()
                                .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                                .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                                .build()
                ).httpStatus(HttpStatus.CONFLICT)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(AppUserUsernameAreadyExistsException.class)
    public InternalApiResponse<String> handleAppUserUsernameAreadyExistsException(AppUserUsernameAreadyExistsException exception) {
        return InternalApiResponse.<String>builder()
                .friendlyMessageResponse(
                        FriendlyMessageResponse.builder()
                                .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                                .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                                .build()
                ).httpStatus(HttpStatus.CONFLICT)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AppUserNotFileUploadException.class)
    public InternalApiResponse<String> handleAppUserNotFileUploadException(AppUserNotFileUploadException exception) {
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
    @ExceptionHandler(AppUserWrongPasswordException.class)
    public InternalApiResponse<String> handleAppUserWrongPasswordException(AppUserWrongPasswordException exception) {
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
    @ExceptionHandler(AppUserPasswordAndConfirmPasswordNotMatchException.class)
    public InternalApiResponse<String> handleAppUserPasswordAndConfirmPasswordNotMatchException(AppUserPasswordAndConfirmPasswordNotMatchException exception) {
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


