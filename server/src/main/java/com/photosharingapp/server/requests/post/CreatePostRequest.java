package com.photosharingapp.server.requests.post;

import com.photosharingapp.server.models.AppUser;

public class CreatePostRequest {
    private String name;
    private String caption;
    private String location;
    private String imageName;
    private AppUser user;
}
