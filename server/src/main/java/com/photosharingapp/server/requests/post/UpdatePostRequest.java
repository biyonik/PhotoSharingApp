package com.photosharingapp.server.requests.post;

import com.photosharingapp.server.models.AppUser;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdatePostRequest {
    private String name;
    private String caption;
    private String location;
    private String imageName;
    private AppUser user;
}
