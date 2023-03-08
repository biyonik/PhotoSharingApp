package com.photosharingapp.server.repositories;

import com.photosharingapp.server.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IAppUserRepository extends JpaRepository<AppUser, UUID> {
    Optional<AppUser> findById(@Param("id") UUID id);
    AppUser findByUsername(@Param("username") String username);
    AppUser findByEmail(@Param("email") String email);
    List<AppUser> findByUsernameContaining(@Param("username") String username);
    List<AppUser> findByUsernameContainingAndIsDeletedFalse(@Param("username") String username);
}
