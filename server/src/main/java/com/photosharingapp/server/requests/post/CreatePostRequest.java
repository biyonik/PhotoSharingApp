package com.photosharingapp.server.requests.post;

import com.photosharingapp.server.models.AppUser;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreatePostRequest {
    private String name;
    private String caption;
    private String location;
    private String imageName;
    private AppUser user;
}
