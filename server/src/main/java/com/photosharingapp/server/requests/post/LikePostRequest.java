package com.photosharingapp.server.requests.post;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class LikePostRequest {
    @NotNull
    private UUID postId;
    @NotNull
    private UUID userId;
}
