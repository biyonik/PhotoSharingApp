package com.photosharingapp.server.services;

import com.photosharingapp.server.enums.Language;
import com.photosharingapp.server.models.Post;
import com.photosharingapp.server.requests.post.CreatePostRequest;
import com.photosharingapp.server.requests.post.DislikePostRequest;
import com.photosharingapp.server.requests.post.LikePostRequest;
import com.photosharingapp.server.requests.post.UpdatePostRequest;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.UUID;

public interface IPostService {
    Post getById(Language language, UUID id);
    Post getByCaption(Language language, String caption);
    List<Post> getAll(Language language);
    List<Post> getByUserId(Language language, UUID userId);
    List<Post> getByUsername(Language language, String username);
    Post create(Language language, CreatePostRequest createPostRequest);
    Post update(Language language, UUID id, UpdatePostRequest updatePostRequest);
    boolean delete(Language language, UUID id);
    boolean changeStatus(Language language, UUID id);
    boolean like(Language language, LikePostRequest likePostRequest);
    boolean dislike(Language language, DislikePostRequest disablePostRequest);
    String savePostImage(Language language, HttpServletRequest request, String fileName);
}
