package com.photosharingapp.server.requests.appuser;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppUserUpdateRequest {
    private String fullname;
    private String username;
    private String email;
    private String bio;
}
