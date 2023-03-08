package com.photosharingapp.server.repositories;

import com.photosharingapp.server.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IPostRepository extends JpaRepository<Post, UUID> {
    List<Post> findAllByOrderByCreatedAtDesc();
    List<Post> findAllByUserUsernameOrderByCreatedAtDesc(@Param("username") String username);
}
