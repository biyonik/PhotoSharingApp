package com.photosharingapp.server.requests.post;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class DislikePostRequest {
    @NotNull
    private UUID postId;
    @NotNull
    private UUID userId;
}
