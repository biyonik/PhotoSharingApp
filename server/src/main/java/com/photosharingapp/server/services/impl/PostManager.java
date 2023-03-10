package com.photosharingapp.server.services.impl;

import com.photosharingapp.server.enums.Language;
import com.photosharingapp.server.exceptions.concrete.post.*;
import com.photosharingapp.server.exceptions.enums.concrete.FriendlyMessageCodes;
import com.photosharingapp.server.models.AppUser;
import com.photosharingapp.server.models.Post;
import com.photosharingapp.server.repositories.IPostRepository;
import com.photosharingapp.server.requests.appuser.AppUserUpdateRequest;
import com.photosharingapp.server.requests.post.CreatePostRequest;
import com.photosharingapp.server.requests.post.DislikePostRequest;
import com.photosharingapp.server.requests.post.LikePostRequest;
import com.photosharingapp.server.requests.post.UpdatePostRequest;
import com.photosharingapp.server.services.IAppUserService;
import com.photosharingapp.server.services.IPostService;
import com.photosharingapp.server.utilities.ConstantUtility;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequiredArgsConstructor
public class PostManager implements IPostService {
    @Autowired
    private final IPostRepository postRepository;
    @Autowired
    private final IAppUserService appUserService;

    @Override
    public Post getById(Language language, UUID id) {
        log.debug("[{}][getById] -> request id: {}", this.getClass().getSimpleName(), id);
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) {
            throw new PostNotFoundException(language, FriendlyMessageCodes.POST_NOT_FOUND, "Post not found for id: " + id);
        }
        log.debug("[{}][getById] -> response: {}", this.getClass().getSimpleName(), post);
        return post.get();
    }

    @Override
    public Post getByCaption(Language language, String caption) {
        log.debug("[{}][getByCaption] -> request id: {}", this.getClass().getSimpleName(), caption);
        Post post = postRepository.findByCaption(caption);
        if (post == null) {
            throw new PostNotFoundException(language, FriendlyMessageCodes.POST_NOT_FOUND, "Post not found for id: " + caption);
        }
        log.debug("[{}][getByCaption] -> response: {}", this.getClass().getSimpleName(), post);
        return post;
    }

    @Override
    public List<Post> getByUserId(Language language, UUID userId) {
        log.debug("[{}][getByUserId] -> request userId: {}", this.getClass().getSimpleName(), userId);
        List<Post> posts = postRepository.findByUserId(userId);
        if (posts.isEmpty()) {
            throw new PostNotFoundException(language, FriendlyMessageCodes.POST_NOT_FOUND, "Any posts not found for userId: " + userId);
        }
        log.debug("[{}][getByUserId] -> response: {}", this.getClass().getSimpleName(), posts);
        return posts;
    }

    @Override
    public List<Post> getByUsername(Language language, String username) {
        log.debug("[{}][getByUserId] -> request username: {}", this.getClass().getSimpleName(), username);
        List<Post> posts = postRepository.findAllByUserUsernameOrderByCreatedAtDesc(username);
        if (posts.isEmpty()) {
            throw new PostNotFoundException(language, FriendlyMessageCodes.POST_NOT_FOUND, "Any posts not found for username: " + username);
        }
        log.debug("[{}][getByUserId] -> response: {}", this.getClass().getSimpleName(), posts);
        return posts;
    }

    @Override
    public List<Post> getAll(Language language) {
        log.debug("[{}][getAll]", this.getClass().getSimpleName());
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        if (posts.isEmpty()) {
            throw new PostNotFoundException(language, FriendlyMessageCodes.POST_NOT_FOUND, "Any posts not found!");
        }
        log.debug("[{}][getAll] -> response: {}", this.getClass().getSimpleName(), posts);
        return posts;
    }

    @Override
    public Post create(Language language, CreatePostRequest createPostRequest) {
        log.debug("[{}][createPost] request: {}", this.getClass().getSimpleName(), createPostRequest);
        try {
            Post post = Post.builder()
                    .caption(createPostRequest.getCaption())
                    .location(createPostRequest.getLocation())
                    .user(createPostRequest.getUser())
                    .name(createPostRequest.getName())
                    .imageName(createPostRequest.getImageName())
                    .build();
            createPostRequest.getUser().setPost(post);
            Post response = postRepository.save(post);
            log.debug("[{}][createPost] response: {}", this.getClass().getSimpleName(), response);
            return response;
        } catch (Exception ex) {
            throw new PostNotCreatedException(language, FriendlyMessageCodes.POST_CREATED_FAILED, "Post creation failed: " + createPostRequest);
        }
    }

    @Override
    public Post update(Language language, UUID id, UpdatePostRequest updatePostRequest) {
        log.debug("[{}][updatePost] -> request: {}", this.getClass().getSimpleName(), updatePostRequest);
        try {
            Post post = getById(language, id);
            post.setName(updatePostRequest.getName());
            post.setCaption(updatePostRequest.getCaption());
            post.setLocation(updatePostRequest.getLocation());
            post.setUser(updatePostRequest.getUser());
            post.setImageName(updatePostRequest.getImageName());
            post.setUpdatedAt(new Date());
            Post postResponse = postRepository.save(post);
            log.debug("[{}][updatePost] -> response: {}", this.getClass().getSimpleName(), postResponse);
            return postResponse;
        } catch (Exception exception) {
            throw new PostNotUpdatedException(language, FriendlyMessageCodes.POST_UPDATED_FAILED, "post request: " + updatePostRequest.toString());
        }
    }

    @Override
    public boolean delete(Language language, UUID id) {
        log.debug("[{}][deletePost] -> request id: {}", this.getClass().getSimpleName(), id);
        try {
            Post post = getById(language, id);
            Files.deleteIfExists(Paths.get(ConstantUtility.POST_FOLDER + "/" + post.getName() + ".png"));
            post.setDeleted(true);
            post.setUpdatedAt(new Date());
            postRepository.save(post);
            log.debug("[{}][deletePost] -> response id: {}", this.getClass().getSimpleName(), id);
            return true;
        } catch (Exception e) {
            throw new PostNotDeletedException(language, FriendlyMessageCodes.POST_DELETE_FAILED, "Post delete failed at id: " + id);
        }
    }

    @Override
    public boolean like(Language language, LikePostRequest likePostRequest) {
        log.debug("[{}][like] -> request: {}", this.getClass().getSimpleName(), likePostRequest);
        try {
            Post post = getById(language, likePostRequest.getPostId());
            AppUser user = appUserService.getUserById(language, likePostRequest.getUserId());
            post.setLikesCount(1);
            user.getLikedPosts().add(post);
            postRepository.save(post);
            AppUserUpdateRequest appUserUpdateRequest = AppUserUpdateRequest.builder()
                    .email(user.getEmail())
                    .bio(user.getBio())
                    .fullname(user.getFullname())
                    .build();
            appUserService.update(language, user.getId(), appUserUpdateRequest);
            log.debug("[{}][like] -> response: {}", this.getClass().getSimpleName(), true);
            return true;
        } catch (Exception ex) {
            throw new PostNotLikedException(language, FriendlyMessageCodes.POST_LIKE_FAILED, "Post not liked at id: "+ likePostRequest.getPostId());
        }
    }

    @Override
    public boolean dislike(Language language, DislikePostRequest disablePostRequest) {
        log.debug("[{}][dislike] -> request: {}", this.getClass().getSimpleName(), disablePostRequest);
        try {
            Post post = getById(language, disablePostRequest.getPostId());
            AppUser user = appUserService.getUserById(language, disablePostRequest.getUserId());
            post.setLikesCount(-1);
            user.getLikedPosts().remove(post);
            postRepository.save(post);
            AppUserUpdateRequest appUserUpdateRequest = AppUserUpdateRequest.builder()
                    .email(user.getEmail())
                    .bio(user.getBio())
                    .fullname(user.getFullname())
                    .build();
            appUserService.update(language, user.getId(), appUserUpdateRequest);
            log.debug("[{}][dislike] -> response: {}", this.getClass().getSimpleName(), true);
            return true;
        } catch (Exception ex) {
            throw new PostNotLikedException(language, FriendlyMessageCodes.POST_DISLIKE_FAILED, "Post not dislike at id: "+ disablePostRequest.getPostId());
        }
    }

    @Override
    public boolean changeStatus(Language language, UUID id) {
        log.debug("[{}][changeStatus] -> request id: {}", this.getClass().getSimpleName(), id);
        try {
            Post post = getById(language, id);
            boolean activeStatus = post.isActive();
            post.setActive(!activeStatus);
            post.setUpdatedAt(new Date());
            postRepository.save(post);
            log.debug("[{}][changeStatus] -> response id: {}", this.getClass().getSimpleName(), id);
            return true;
        } catch (Exception e) {
            throw new PostNotChangeActiveStatusException(language, FriendlyMessageCodes.POST_ACTIVE_STATUS_CHANGE_FAILED, "Post change active status at id: " + id);
        }
    }

    @Override
    public String savePostImage(Language language, HttpServletRequest request, String fileName) {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
        MultipartFile multipartFile = multipartHttpServletRequest.getFile(iterator.next());
        try {
            byte[] bytes = multipartFile != null ? multipartFile.getBytes() : new byte[0];
            Path path = Paths.get("%s/%s.png".formatted(ConstantUtility.POST_FOLDER, fileName));
            Files.write(path, bytes, StandardOpenOption.CREATE);
            return fileName;
        } catch (Exception ex) {
            throw new PostNotFileUploadException(language, FriendlyMessageCodes.POST_FILE_UPLOAD_FAILED, "File upload operation failed for file name: " + fileName);
        }
    }
}
