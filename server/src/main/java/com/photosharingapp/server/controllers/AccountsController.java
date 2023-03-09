package com.photosharingapp.server.controllers;

import com.photosharingapp.server.enums.Language;
import com.photosharingapp.server.exceptions.enums.concrete.FriendlyMessageCodes;
import com.photosharingapp.server.exceptions.utils.FriendlyMessageUtils;
import com.photosharingapp.server.models.AppUser;
import com.photosharingapp.server.requests.appuser.AppUserCreateRequest;
import com.photosharingapp.server.requests.appuser.AppUserUpdateRequest;
import com.photosharingapp.server.requests.appuser.ChangePasswordRequest;
import com.photosharingapp.server.responses.FriendlyMessageResponse;
import com.photosharingapp.server.responses.InternalApiResponse;
import com.photosharingapp.server.responses.concrete.AppUserResponse;
import com.photosharingapp.server.services.IAppUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
@RequestMapping(value = "/api/1.0/account")
@RequiredArgsConstructor
public class AccountsController {
    private String appUserImageId;
    @Autowired
    private final IAppUserService appUserService;

    @GetMapping(value = "/{language}")
    public InternalApiResponse<List<AppUserResponse>> getAll(@PathVariable("language") Language language) {
        log.debug("[{}][getAllUsers]", this.getClass().getSimpleName());
        List<AppUser> appUsers = appUserService.getUsers(language);
        List<AppUserResponse> appUsersResponseList = convertAppUserToAppUserResponseList(appUsers);
        log.debug("[{}][getAllUsers] -> response: {}", this.getClass().getSimpleName(), appUsersResponseList);
        return InternalApiResponse.<List<AppUserResponse>>builder()
                .friendlyMessageResponse(FriendlyMessageResponse.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, null))
                        .build()
                )
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(appUsersResponseList)
                .build();
    }

    @GetMapping(value = "/{language}/{username}")
    public InternalApiResponse<AppUserResponse> getUserByUsername(@PathVariable("language") Language language, @PathVariable("username") String username) {
        log.debug("[{}][getUserByUsername] -> request username: {}", this.getClass().getSimpleName(), username);
        AppUser appUser = appUserService.getByUsername(language, username);
        AppUserResponse response = convertAppUserToAppUserResponse(appUser);
        log.debug("[{}][getUserByUsername] -> response: {}", this.getClass().getSimpleName(), response);
        return InternalApiResponse.<AppUserResponse>builder()
                .friendlyMessageResponse(FriendlyMessageResponse.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, null))
                        .build()
                )
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(response)
                .build();
    }

    @GetMapping(value = "/{language}/all/{username}")
    public InternalApiResponse<List<AppUserResponse>> getUserListByUsername(@PathVariable("language") Language language, @PathVariable("username") String username, @RequestBody boolean onlyNotDeleted) {
        log.debug("[{}][getUserListByUsername]", this.getClass().getSimpleName());
        List<AppUser> appUsers;
        if (!onlyNotDeleted) {
            appUsers = appUserService.getUserListByUsername(language, username);
        } else {
            appUsers = appUserService.getUserListByUsernameAndIsDeletedFalse(language, username);
        }
        List<AppUserResponse> appUsersResponseList = convertAppUserToAppUserResponseList(appUsers);
        log.debug("[{}][getUserListByUsername] -> response: {}", this.getClass().getSimpleName(), appUsersResponseList);
        return InternalApiResponse.<List<AppUserResponse>>builder()
                .friendlyMessageResponse(FriendlyMessageResponse.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, null))
                        .build()
                )
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(appUsersResponseList)
                .build();
    }

    @PostMapping("/{language}/register")
    public InternalApiResponse<AppUserResponse> register(@PathVariable("language") Language language, @RequestBody AppUserCreateRequest appUserCreateRequest) {
        log.debug("[{}][register] -> {}", this.getClass().getSimpleName(), appUserCreateRequest);
        AppUser user = appUserService.create(language, appUserCreateRequest);
        AppUserResponse appUserResponse = convertAppUserToAppUserResponse(user);
        log.debug("[{}][register] -> {}", this.getClass().getSimpleName(), appUserResponse);
        return InternalApiResponse.<AppUserResponse>builder()
                .httpStatus(HttpStatus.OK)
                .friendlyMessageResponse(FriendlyMessageResponse.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.USER_REGISTER_SUCCESSFULLY))
                        .build()
                )
                .hasError(false)
                .payload(appUserResponse)
                .build();
    }

    @PutMapping("/{language}/{id}")
    public InternalApiResponse<AppUserResponse> update(@PathVariable("language") Language language, UUID id, @RequestBody AppUserUpdateRequest appUserUpdateRequest) {
        log.debug("[{}][update] -> {}", this.getClass().getSimpleName(), appUserUpdateRequest);
        AppUser user = appUserService.update(language, id, appUserUpdateRequest);
        AppUserResponse appUserResponse = convertAppUserToAppUserResponse(user);
        appUserImageId = user.getId().toString();
        log.debug("[{}][update] -> {}", this.getClass().getSimpleName(), appUserResponse);
        return InternalApiResponse.<AppUserResponse>builder()
                .friendlyMessageResponse(FriendlyMessageResponse.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.USER_UPDATED_SUCCESSFULLY))
                        .build()
                )
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(appUserResponse)
                .build();
    }

    @PostMapping("/{language}/photo/upload")
    public InternalApiResponse<String> fileUpload(@PathVariable("language") Language language, HttpServletRequest request) {
        log.debug("[{}][fileUpload] -> request: {}", this.getClass().getSimpleName(), request);
        String response = appUserService.savePhoto(language, request, appUserImageId);
        log.debug("[{}][fileUpload] -> response: {}", this.getClass().getSimpleName(), response);
        return InternalApiResponse.<String>builder()
                .friendlyMessageResponse(FriendlyMessageResponse.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.USER_PHOTO_UPLOAD_SUCCESSFULLY))
                        .build()
                )
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(response)
                .build();
    }

    @PutMapping("/{language}/changePassword/{id}")
    public InternalApiResponse<Boolean> changePassword(@PathVariable("language") Language language, @PathVariable("id") UUID id, @Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        log.debug("[{}][changePassword] -> request for id: {}", this.getClass().getSimpleName(), id);
        Boolean response = appUserService.changePassword(language, id, changePasswordRequest);
        log.debug("[{}][changePassword] -> response: {}", this.getClass().getSimpleName(), response);
        return InternalApiResponse.<Boolean>builder()
                .friendlyMessageResponse(FriendlyMessageResponse.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.USER_CHANGE_PASSWORD_SUCCESSFULLY))
                        .build()
                )
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(response)
                .build();
    }

    @PutMapping("/{language}/resetPassword/{id}")
    public InternalApiResponse<Boolean> resetPassword(@PathVariable("language") Language language, @PathVariable("id") UUID id) {
        log.debug("[{}][resetPassword] -> request for id: {}", this.getClass().getSimpleName(), id);
        Boolean response = appUserService.resetPassword(language, id);
        log.debug("[{}][resetPassword] -> response: {}", this.getClass().getSimpleName(), response);
        return InternalApiResponse.<Boolean>builder()
                .friendlyMessageResponse(FriendlyMessageResponse.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.USER_CHANGE_PASSWORD_SUCCESSFULLY))
                        .build()
                )
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(response)
                .build();
    }

    @PutMapping("/{language}/changeStatus/{id}")
    public InternalApiResponse<Boolean> changeStatus(@PathVariable("language") Language language, @PathVariable("id") UUID id) {
        log.debug("[{}][changeStatus] -> request for id: {}", this.getClass().getSimpleName(), id);
        Boolean response = appUserService.changeActiveStatus(language, id);
        log.debug("[{}][changeStatus] -> response: {}", this.getClass().getSimpleName(), response);
        return InternalApiResponse.<Boolean>builder()
                .friendlyMessageResponse(FriendlyMessageResponse.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.USER_ACTIVE_STATUS_CHANGE_SUCCESSFULLY))
                        .build()
                )
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(response)
                .build();
    }

    @DeleteMapping("/{language}/{id}")
    public InternalApiResponse<Boolean> delete(@PathVariable("language") Language language, @PathVariable("id") UUID id) {
        log.debug("[{}][delete] -> request for id: {}", this.getClass().getSimpleName(), id);
        Boolean response = appUserService.delete(language, id);
        log.debug("[{}][delete] -> response: {}", this.getClass().getSimpleName(), response);
        return InternalApiResponse.<Boolean>builder()
                .friendlyMessageResponse(FriendlyMessageResponse.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.USER_DELETE_SUCCESSFULLY))
                        .build()
                )
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(response)
                .build();
    }

    private static List<AppUserResponse> convertAppUserToAppUserResponseList(@NotNull List<AppUser> appUsers) {
        return appUsers.stream()
                .map(arg -> AppUserResponse.builder()
                        .id(arg.getId())
                        .bio(arg.getBio())
                        .createdAt(arg.getCreatedAt().getTime())
                        .email(arg.getEmail())
                        .updatedAt(arg.getUpdatedAt().getTime())
                        .fullname(arg.getFullname())
                        .isActive(arg.isActive())
                        .isDeleted(arg.isDeleted())
                        .username(arg.getUsername())
                        .build()
                ).collect(Collectors.toList());
    }

    private static AppUserResponse convertAppUserToAppUserResponse(@NotNull AppUser appUser) {
        return AppUserResponse.builder()
                .id(appUser.getId())
                .bio(appUser.getBio())
                .createdAt(appUser.getCreatedAt().getTime())
                .email(appUser.getEmail())
                .updatedAt(appUser.getUpdatedAt().getTime())
                .fullname(appUser.getFullname())
                .isActive(appUser.isActive())
                .isDeleted(appUser.isDeleted())
                .username(appUser.getUsername())
                .build();
    }
}
