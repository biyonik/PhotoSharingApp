package com.photosharingapp.server.requests.appuser;

import lombok.Getter;

@Getter
public class AppUserCreateRequest {
    private String fullname;
    private String username;
    private String email;
    private String bio;
}
