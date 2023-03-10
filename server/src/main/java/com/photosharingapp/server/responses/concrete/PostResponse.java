package com.photosharingapp.server.responses.concrete;

import com.photosharingapp.server.models.AppUser;
import com.photosharingapp.server.models.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
public class PostResponse {
    private UUID id;
    private String name;
    private String caption;
    private String location;
    private Integer likesCount;
    private Long createdAt;
    private Long updatedAt;
    private boolean isActive;
    private boolean isDeleted;
    private AppUser user;
    private List<Comment> comments;
}
