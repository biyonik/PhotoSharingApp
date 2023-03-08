package com.photosharingapp.server.services.impl;

import com.photosharingapp.server.repositories.IRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleManager {
    private final IRoleRepository roleRepository;
}
