package com.photosharingapp.server.requests.comment;

import com.photosharingapp.server.models.AppUser;
import com.photosharingapp.server.models.Post;
import lombok.Getter;

@Getter
public class CreateCommentRequest {
    private String content;
    private AppUser appUser;
    private Post post;
}
