package com.photosharingapp.server.responses.concrete;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class AppUserResponse {
    private UUID id;
    private String fullname;
    private String username;
    private String email;
    private String bio;
    private Long createdAt;
    private Long updatedAt;
    private boolean isActive;
    private boolean isDeleted;
}
