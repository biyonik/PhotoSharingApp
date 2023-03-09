package com.photosharingapp.server.repositories;

import com.photosharingapp.server.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ICommentRepository  extends JpaRepository<Comment, UUID> {
    Optional<Comment> findById(@Param("id") UUID id);
    List<Comment> findByPostId(@Param("postId") UUID postId);
    List<Comment> findByUserId(@Param("userId") UUID userId);
    List<Comment> findByPostIdAndIsDeletedFalseOrderByCreatedAtDesc(@Param("postId") UUID postId);
}
