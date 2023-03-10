package com.photosharingapp.server.responses.concrete;

import com.photosharingapp.server.models.AppUser;
import com.photosharingapp.server.models.Post;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CommentResponse {
    private UUID id;
    private String content;
    private AppUser user;
    private Post post;
    private Long createdAt;
    private Long updatedAt;
    private Boolean isActive;
    private Boolean isDeleted;
}
