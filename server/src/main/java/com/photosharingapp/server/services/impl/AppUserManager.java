package com.photosharingapp.server.services.impl;

import com.photosharingapp.server.enums.Language;
import com.photosharingapp.server.exceptions.concrete.appuser.*;
import com.photosharingapp.server.exceptions.concrete.post.PostNotFileUploadException;
import com.photosharingapp.server.exceptions.enums.concrete.FriendlyMessageCodes;
import com.photosharingapp.server.models.AppUser;
import com.photosharingapp.server.repositories.IAppUserRepository;
import com.photosharingapp.server.repositories.IRoleRepository;
import com.photosharingapp.server.requests.appuser.AppUserCreateRequest;
import com.photosharingapp.server.requests.appuser.AppUserUpdateRequest;
import com.photosharingapp.server.requests.appuser.ChangePasswordRequest;
import com.photosharingapp.server.services.IAppUserService;
import com.photosharingapp.server.utilities.ConstantUtility;
import com.photosharingapp.server.utilities.EmailUtility;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class AppUserManager implements IAppUserService {
    @Autowired
    private final IAppUserRepository appUserRepository;
    @Autowired
    private final IRoleRepository roleRepository;
    @Autowired
    private final EmailUtility emailUtility;
    @Autowired
    private final JavaMailSender javaMailSender;
    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public AppUser getUserById(Language language, UUID id) {
        log.debug("[{}][getUserById] -> request id: {}", this.getClass().getSimpleName(), id);
        Optional<AppUser> user = appUserRepository.findById(id);
        if (user.isEmpty()) {
            throw new AppUserNotFoundException(language, FriendlyMessageCodes.USER_NOT_FOUND, "App user not found for id: " + id);
        }
        log.debug("[{}][getUserById] -> response: {}", this.getClass().getSimpleName(), user);
        return user.get();
    }

    @Override
    public AppUser getByUsername(Language language, String username) {
        log.debug("[{}][getByUsername] -> request username: {}", this.getClass().getSimpleName(), username);
        AppUser user = appUserRepository.findByUsername(username);
        if (user == null) {
            throw new AppUserNotFoundException(language, FriendlyMessageCodes.USER_NOT_FOUND, "App user not found for username: " + username);
        }
        log.debug("[{}][getByUsername] -> response: {}", this.getClass().getSimpleName(), user);
        return user;
    }

    @Override
    public AppUser getByEmail(Language language, String email) {
        log.debug("[{}][getByEmail] -> request email: {}", this.getClass().getSimpleName(), email);
        AppUser user = appUserRepository.findByEmail(email);
        if (user == null) {
            throw new AppUserNotFoundException(language, FriendlyMessageCodes.USER_NOT_FOUND, "App user not found for email: " + email);
        }
        log.debug("[{}][getByEmail] -> response: {}", this.getClass().getSimpleName(), user);
        return user;
    }

    @Override
    public List<AppUser> getUsers(Language language) {
        log.debug("[{}][getUsers]", this.getClass().getSimpleName());
        List<AppUser> users = appUserRepository.findAll();
        if (users.isEmpty()) {
            throw new AppUserNotFoundException(language, FriendlyMessageCodes.USER_NOT_FOUND, "Any users not found!");
        }
        log.debug("[{}][getUsers] -> response: {}", this.getClass().getSimpleName(), users);
        return users;
    }

    @Override
    public List<AppUser> getUserListByUsername(Language language, String username) {
        log.debug("[{}][getUserListByUsername]", this.getClass().getSimpleName());
        List<AppUser> users = appUserRepository.findByUsernameContaining(username);
        if (users.isEmpty()) {
            throw new AppUserNotFoundException(language, FriendlyMessageCodes.USER_NOT_FOUND, "Any users not found!");
        }
        log.debug("[{}][getUserListByUsername] -> response: {}", this.getClass().getSimpleName(), users);
        return users;
    }

    @Override
    public List<AppUser> getUserListByUsernameAndIsDeletedFalse(Language language, String username) {
        log.debug("[{}][getUserListByUsernameAndIsDeletedFalse]", this.getClass().getSimpleName());
        List<AppUser> users = appUserRepository.findByUsernameContainingAndIsDeletedFalse(username);
        if (users.isEmpty()) {
            throw new AppUserNotFoundException(language, FriendlyMessageCodes.USER_NOT_FOUND, "Any users not found!");
        }
        log.debug("[{}][getUserListByUsernameAndIsDeletedFalse] -> response: {}", this.getClass().getSimpleName(), users);
        return users;
    }

    @Override
    public AppUser create(Language language, AppUserCreateRequest appUserCreateRequest) {
        log.debug("[{}][createUser] request: {}", this.getClass().getSimpleName(), appUserCreateRequest);
        try {
            AppUser emailIsExists = getByEmail(language, appUserCreateRequest.getEmail());
            if (emailIsExists != null) {
                throw new AppUserEmailAreadyExistsException(language, FriendlyMessageCodes.USER_EMAIL_ALREADY_EXISTS, "Email already exists error. Email: " + appUserCreateRequest.getEmail());
            }

            AppUser usernameIsExists = getByUsername(language, appUserCreateRequest.getUsername());
            if (usernameIsExists != null) {
                throw new AppUserUsernameAreadyExistsException(language, FriendlyMessageCodes.USER_USERNAME_ALREADY_EXISTS, "Username already exists error. Username: " + appUserCreateRequest.getUsername());
            }

            String password = RandomStringUtils.randomAlphabetic(10);
            String encyrptedPassword = bCryptPasswordEncoder.encode(password);
            AppUser appUser = AppUser.builder()
                    .fullname(appUserCreateRequest.getFullname())
                    .username(appUserCreateRequest.getUsername())
                    .password(encyrptedPassword)
                    .bio(appUserCreateRequest.getBio())
                    .build();
            AppUser appUserResponse = appUserRepository.save(appUser);
            javaMailSender.send(emailUtility.constructNewUserEmail(appUserResponse, password));
            log.debug("[{}][createUser] -> response: {}", this.getClass().getSimpleName(), appUserResponse);
            return appUserResponse;
        } catch (Exception exception) {
            throw new AppUserNotCreatedException(language, FriendlyMessageCodes.USER_CREATED_FAILED, "User request: " + appUserCreateRequest);
        }
    }

    @Override
    public AppUser update(Language language, UUID id, AppUserUpdateRequest appUserUpdateRequest) {
        log.debug("[{}][updateUser] -> request: {}", this.getClass().getSimpleName(), appUserUpdateRequest);
        try {
            AppUser user = getUserById(language, id);

            if (!user.getEmail().equals(appUserUpdateRequest.getEmail())) {
                AppUser emailIsExists = getByEmail(language, appUserUpdateRequest.getEmail());
                if (emailIsExists != null) {
                    throw new AppUserEmailAreadyExistsException(language, FriendlyMessageCodes.USER_EMAIL_ALREADY_EXISTS, "Email already exists error. Email: " + appUserUpdateRequest.getEmail());
                }
            }

            if (!user.getUsername().equals(appUserUpdateRequest.getUsername())) {
                AppUser usernameIsExists = getByUsername(language, appUserUpdateRequest.getUsername());
                if (usernameIsExists != null) {
                    throw new AppUserUsernameAreadyExistsException(language, FriendlyMessageCodes.USER_USERNAME_ALREADY_EXISTS, "Username already exists error. Username: " + appUserUpdateRequest.getUsername());
                }
            }

            user.setFullname(appUserUpdateRequest.getFullname());
            user.setUsername(appUserUpdateRequest.getUsername());
            user.setEmail(appUserUpdateRequest.getEmail());
            user.setBio(appUserUpdateRequest.getBio());
            user.setCreatedAt(user.getCreatedAt());
            user.setUpdatedAt(new Date());
            AppUser userResponse = appUserRepository.save(user);
            log.debug("[{}][updateUser] -> response: {}", this.getClass().getSimpleName(), userResponse);
            return userResponse;
        } catch (Exception exception) {
            throw new AppUserNotUpdatedException(language, FriendlyMessageCodes.USER_UPDATED_FAILED, "appuser request: " + appUserUpdateRequest.toString());
        }
    }

    @Override
    public boolean delete(Language language, UUID id) {
        log.debug("[{}][deleteUser] -> request id: {}", this.getClass().getSimpleName(), id);
        AppUser user;
        try {
            user = getUserById(language, id);
            user.setDeleted(true);
            user.setUpdatedAt(new Date());
            appUserRepository.save(user);
            log.debug("[{}][deleteUser] -> response id: {}", this.getClass().getSimpleName(), id);
            return true;
        } catch (Exception e) {
            throw new AppUserNotDeletedException(language, FriendlyMessageCodes.USER_DELETE_FAILED, "User already deleted for id: " + id);
        }
    }

    @Override
    public boolean changePassword(Language language, UUID id, ChangePasswordRequest changePasswordRequest) {
        log.debug("[{}][changePassword] -> request id: {}", this.getClass().getSimpleName(), id);
        try {
            AppUser appUserById = getUserById(language, id);
            if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmNewPassword())) {
                throw new AppUserPasswordAndConfirmPasswordNotMatchException(language, FriendlyMessageCodes.USER_PASSWORD_AND_CONFIRM_PASSWORD_NOT_MATCH, "Change failed. Because new password and confirm password does not match!");
            }

            if (!bCryptPasswordEncoder.matches(changePasswordRequest.getOldPassword(), appUserById.getPassword())) {
                throw new AppUserWrongPasswordException(language, FriendlyMessageCodes.USER_PASSWORD_WRONG, "Password change failed. Because password is wrong! Does not matches current password");
            }

            String encryptedPassword = bCryptPasswordEncoder.encode(changePasswordRequest.getNewPassword());
            appUserById.setPassword(encryptedPassword);
            appUserRepository.save(appUserById);
            javaMailSender.send(emailUtility.constructUpdatePasswordEmail(appUserById, changePasswordRequest.getNewPassword()));
            log.debug("[{}][changePassword] -> request id: {}", this.getClass().getSimpleName(), id);
            return true;
        } catch (Exception exception) {
            throw new AppUserNotChangePasswordsException(language, FriendlyMessageCodes.USER_CHANGE_PASSWORD_FAILED, "Password change failed at user id: " + id);
        }
    }

    @Override
    public boolean resetPassword(Language language, UUID id) {
        log.debug("[{}][resetPassword] -> request id: {}", this.getClass().getSimpleName(), id);
        try {
            AppUser appUserById = getUserById(language, id);
            String password = RandomStringUtils.randomAlphabetic(10);
            String encryptedPassword = bCryptPasswordEncoder.encode(password);
            appUserById.setPassword(encryptedPassword);
            appUserRepository.save(appUserById);
            javaMailSender.send(emailUtility.constructResetPasswordEmail(appUserById, password));
            log.debug("[{}][resetPassword] -> request id: {}", this.getClass().getSimpleName(), id);
            return true;
        } catch (Exception exception) {
            throw new AppUserNotChangePasswordsException(language, FriendlyMessageCodes.USER_CHANGE_PASSWORD_FAILED, "Password change failed at user id: " + id);
        }
    }

    @Override
    public boolean changeActiveStatus(Language language, UUID id) {
        log.debug("[{}][changeActiveStatus] -> request id: {}", this.getClass().getSimpleName(), id);
        try {
            AppUser user = getUserById(language, id);
            boolean activeStatus = user.isActive();
            user.setActive(!activeStatus);
            user.setUpdatedAt(new Date());
            appUserRepository.save(user);
            log.debug("[{}][changeActiveStatus] -> response id: {}", this.getClass().getSimpleName(), id);
            return true;
        } catch (Exception e) {
            throw new AppUserNotChangeActiveStatusException(language, FriendlyMessageCodes.USER_ACTIVE_STATUS_CHANGE_FAILED, "AppUser change active status at id: " + id);
        }
    }

    @Override
    public String savePhoto(Language language, HttpServletRequest request, String filename) {
        log.debug("[{}][savePhoto] -> request: {}", this.getClass().getSimpleName(), request);
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
        MultipartFile multipartFile = multipartHttpServletRequest.getFile(iterator.next());
        try {
            byte[] bytes = multipartFile != null ? multipartFile.getBytes() : new byte[0];
            Path path = Paths.get("%s/%s.png".formatted(ConstantUtility.USER_FOLDER, filename));
            Files.write(path, bytes, StandardOpenOption.CREATE);
            log.debug("[{}][savePhoto] -> response: {}", this.getClass().getSimpleName(), path);
            return path.toAbsolutePath().toString();
        } catch (Exception ex) {
            throw new PostNotFileUploadException(language, FriendlyMessageCodes.USER_PHOTO_UPLOAD_FAILED, "File upload operation failed for file name: " + filename);
        }

    }
}
