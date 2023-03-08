package com.photosharingapp.server.services.impl;

import com.photosharingapp.server.repositories.ICommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentManager {
    private final ICommentRepository commentRepository;
}
