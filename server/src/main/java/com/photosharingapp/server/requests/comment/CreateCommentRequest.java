package com.photosharingapp.server.requests.comment;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateCommentRequest {
    private String content;
    private UUID userId;
    private UUID postId;
}
