package com.photosharingapp.server.requests.appuser;

import lombok.Getter;

@Getter
public class AppUserUpdateRequest {
    private String fullname;
    private String username;
    private String email;
    private String bio;
}
