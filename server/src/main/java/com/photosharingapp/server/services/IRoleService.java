package com.photosharingapp.server.services;

import com.photosharingapp.server.enums.Language;
import com.photosharingapp.server.models.Role;
import com.photosharingapp.server.requests.role.CreateRoleRequest;
import com.photosharingapp.server.requests.role.UpdateRoleRequest;

import java.util.UUID;

public interface IRoleService {
    Role getRoleById(Language language, UUID id);
    Role getRoleByName(Language language, String role);
    Role create(Language language, CreateRoleRequest createRoleRequest);
    Role update(Language language, UUID id, UpdateRoleRequest updateRoleRequest);
}
