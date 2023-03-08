package com.photosharingapp.server.services;

import com.photosharingapp.server.enums.Language;
import com.photosharingapp.server.models.Post;
import com.photosharingapp.server.requests.post.CreatePostRequest;
import com.photosharingapp.server.requests.post.UpdatePostRequest;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.UUID;

public interface IPostService {
    Post getById(Language language, UUID id);
    Post getByCaption(Language language, String caption);
    List<Post> getAll(Language language);
    List<Post> getByUserId(Language language, UUID userId);
    Post create(Language language, CreatePostRequest createPostRequest);
    Post update(Language language, UpdatePostRequest updatePostRequest);
    void Delete(Language language, UUID id);
    void changeStatus(Language language, UUID id);
    String savePostImage(HttpServletRequest request, String fileName);
}
