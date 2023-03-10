package com.photosharingapp.server.controllers;

import com.photosharingapp.server.enums.Language;
import com.photosharingapp.server.exceptions.enums.concrete.FriendlyMessageCodes;
import com.photosharingapp.server.exceptions.utils.FriendlyMessageUtils;
import com.photosharingapp.server.models.Post;
import com.photosharingapp.server.requests.post.CreatePostRequest;
import com.photosharingapp.server.requests.post.DislikePostRequest;
import com.photosharingapp.server.requests.post.LikePostRequest;
import com.photosharingapp.server.responses.FriendlyMessageResponse;
import com.photosharingapp.server.responses.InternalApiResponse;
import com.photosharingapp.server.responses.concrete.PostResponse;
import com.photosharingapp.server.services.IAppUserService;
import com.photosharingapp.server.services.ICommentService;
import com.photosharingapp.server.services.IPostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController()
@RequestMapping(value = "/api/1.0/post")
@RequiredArgsConstructor
public class PostsController {
    private String postImageName;
    @Autowired
    private final IPostService postService;
    @Autowired
    private final IAppUserService appUserService;
    @Autowired
    private final ICommentService commentService;

    @GetMapping(value = "/{language}")
    public InternalApiResponse<List<PostResponse>> getAll(@PathVariable("language") Language language) {
        log.debug("[{}][getAllPosts]", this.getClass().getSimpleName());
        List<Post> posts = postService.getAll(language);
        List<PostResponse> postResponseList = convertPostListToPostResponseList(posts);
        log.debug("[{}][getAllPosts] -> response: {}", this.getClass().getSimpleName(), postResponseList);
        return InternalApiResponse.<List<PostResponse>>builder()
                .friendlyMessageResponse(FriendlyMessageResponse.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, null))
                        .build()
                )
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(postResponseList)
                .build();
    }

    @GetMapping(value = "/{language}/user?username={username}")
    public InternalApiResponse<List<PostResponse>> getAllPostsFromUsername(@PathVariable("language") Language language, @PathVariable("username") String username) {
        log.debug("[{}][getAllPostsFromUsername]", this.getClass().getSimpleName());
        List<Post> posts = postService.getByUsername(language, username);
        List<PostResponse> postResponseList = convertPostListToPostResponseList(posts);
        log.debug("[{}][getAllPostsFromUsername] -> response: {}", this.getClass().getSimpleName(), postResponseList);
        return InternalApiResponse.<List<PostResponse>>builder()
                .friendlyMessageResponse(FriendlyMessageResponse.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, null))
                        .build()
                )
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(postResponseList)
                .build();
    }

    @GetMapping(value = "/{language}/user?id={id}")
    public InternalApiResponse<List<PostResponse>> getAllPostsFromUserId(@PathVariable("language") Language language, @PathVariable("id") UUID id) {
        log.debug("[{}][getAllPostsFromUserId]", this.getClass().getSimpleName());
        List<Post> posts = postService.getByUserId(language, id);
        List<PostResponse> postResponseList = convertPostListToPostResponseList(posts);
        log.debug("[{}][getAllPostsFromUserId] -> response: {}", this.getClass().getSimpleName(), postResponseList);
        return InternalApiResponse.<List<PostResponse>>builder()
                .friendlyMessageResponse(FriendlyMessageResponse.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, null))
                        .build()
                )
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(postResponseList)
                .build();
    }

    @GetMapping(value="/{language}/{id}")
    public InternalApiResponse<PostResponse> getById(@PathVariable("language")Language language, @PathVariable("id")UUID id) {
        log.debug("[{}][getPostById]", this.getClass().getSimpleName());
        Post post = postService.getById(language, id);
        PostResponse postResponse = convertPostToPostResponse(post);
        log.debug("[{}][getPostById] -> response: {}", this.getClass().getSimpleName(), postResponse);
        return InternalApiResponse.<PostResponse>builder()
                .friendlyMessageResponse(FriendlyMessageResponse.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, null))
                        .build()
                )
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(postResponse)
                .build();
    }

    @PostMapping(value = "/{language}")
    public InternalApiResponse<PostResponse> create(@PathVariable("language") Language language, @RequestBody CreatePostRequest createPostRequest) {
        log.debug("[{}][create] -> {}", this.getClass().getSimpleName(), createPostRequest);
        postImageName = RandomStringUtils.randomAlphabetic(10);
        createPostRequest.setImageName(postImageName);
        Post post = postService.create(language, createPostRequest);
        PostResponse postResponse = convertPostToPostResponse(post);
        log.debug("[{}][create] -> {}", this.getClass().getSimpleName(), postResponse);
        return InternalApiResponse.<PostResponse>builder()
                .httpStatus(HttpStatus.OK)
                .friendlyMessageResponse(FriendlyMessageResponse.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.POST_CREATED_SUCCESSFULLY))
                        .build()
                )
                .hasError(false)
                .payload(postResponse)
                .build();
    }

    @PostMapping(value = "/{language}/like")
    public InternalApiResponse<Boolean> like(@PathVariable("language") Language language, @Valid @RequestBody LikePostRequest likePostRequest) {
        log.debug("[{}][like] -> request: {}", this.getClass().getSimpleName(), likePostRequest);
        Boolean response = postService.like(language, likePostRequest);
        log.debug("[{}][like] -> response: {}", this.getClass().getSimpleName(), response);
        return InternalApiResponse.<Boolean>builder()
                .httpStatus(HttpStatus.OK)
                .friendlyMessageResponse(FriendlyMessageResponse.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.POST_LIKE_SUCCESSFULLY))
                        .build()
                )
                .hasError(false)
                .payload(response)
                .build();
    }

    @PostMapping(value = "/{language}/dislike")
    public InternalApiResponse<Boolean> dislike(@PathVariable("language") Language language, @Valid @RequestBody DislikePostRequest dislikePostRequest) {
        log.debug("[{}][dislike] -> request: {}", this.getClass().getSimpleName(), dislikePostRequest);
        Boolean response = postService.dislike(language, dislikePostRequest);
        log.debug("[{}][dislike] -> response: {}", this.getClass().getSimpleName(), response);
        return InternalApiResponse.<Boolean>builder()
                .httpStatus(HttpStatus.OK)
                .friendlyMessageResponse(FriendlyMessageResponse.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.POST_DISLIKE_SUCCESSFULLY))
                        .build()
                )
                .hasError(false)
                .payload(response)
                .build();
    }

    @PostMapping("/{language}/photo/upload")
    public InternalApiResponse<String> photoUpload(@PathVariable("language") Language language, HttpServletRequest request) {
        log.debug("[{}][photoUpload] -> request: {}", this.getClass().getSimpleName(), request);
        String response = postService.savePostImage(language, request, postImageName);
        log.debug("[{}][photoUpload] -> response: {}", this.getClass().getSimpleName(), response);
        return InternalApiResponse.<String>builder()
                .friendlyMessageResponse(FriendlyMessageResponse.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.POST_FILE_UPLOAD_SUCCESSFULLY))
                        .build()
                )
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(response)
                .build();
    }

    @DeleteMapping(value="/{language}/{id}")
    public InternalApiResponse<Boolean> delete(@PathVariable("language") Language language, @PathVariable("id") UUID id) {
        log.debug("[{}][delete] -> request for id: {}", this.getClass().getSimpleName(), id);
        Boolean response = postService.delete(language, id);
        log.debug("[{}][delete] -> response: {}", this.getClass().getSimpleName(), response);
        return InternalApiResponse.<Boolean>builder()
                .friendlyMessageResponse(FriendlyMessageResponse.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.POST_DELETE_SUCCESSFULLY))
                        .build()
                )
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(response)
                .build();
    }

    private static List<PostResponse> convertPostListToPostResponseList(@NotNull List<Post> posts) {
        return posts.stream()
                .map(arg -> PostResponse.builder()
                        .id(arg.getId())
                        .caption(arg.getCaption())
                        .name(arg.getName())
                        .isDeleted(arg.isDeleted())
                        .location(arg.getLocation())
                        .createdAt(arg.getCreatedAt().getTime())
                        .updatedAt(arg.getUpdatedAt().getTime())
                        .isActive(arg.isActive())
                        .isDeleted(arg.isDeleted())
                        .comments(arg.getComments())
                        .user(arg.getUser())
                        .likesCount(arg.getLikesCount())
                        .build()
                ).collect(Collectors.toList());
    }

    private static PostResponse convertPostToPostResponse(@NotNull Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .caption(post.getCaption())
                .name(post.getName())
                .isDeleted(post.isDeleted())
                .location(post.getLocation())
                .createdAt(post.getCreatedAt().getTime())
                .updatedAt(post.getUpdatedAt().getTime())
                .isActive(post.isActive())
                .isDeleted(post.isDeleted())
                .comments(post.getComments())
                .user(post.getUser())
                .likesCount(post.getLikesCount())
                .build();
    }
}
