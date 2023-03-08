package com.photosharingapp.server.services;

import com.photosharingapp.server.enums.Language;
import com.photosharingapp.server.models.AppUser;
import com.photosharingapp.server.requests.appuser.AppUserCreateRequest;
import com.photosharingapp.server.requests.appuser.AppUserUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface IAppUserService {
    AppUser getUserById(Language language, UUID id);
    AppUser getByUsername(Language language, String username);
    AppUser getByEmail(Language language, String email);
    List<AppUser> getUsers(Language language);
    List<AppUser> getUserListByUsername(Language language, String username);
    List<AppUser> getUserListByUsernameAndIsDeletedFalse(Language language, String username);
    AppUser create(Language language, AppUserCreateRequest appUserCreateRequest);
    AppUser update(Language language, UUID id, AppUserUpdateRequest appUserUpdateRequest);
    boolean delete(Language language, UUID id);
    boolean changePassword(Language language, UUID id, String password);
    boolean resetPassword(Language language, UUID id);
    boolean changeActiveStatus(Language language, UUID id);
}
