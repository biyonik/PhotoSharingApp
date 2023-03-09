package com.photosharingapp.server.requests.appuser;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ChangePasswordRequest {
    @NotNull
    String oldPassword;
    @NotNull
    String newPassword;
    @NotNull
    String confirmNewPassword;
}
