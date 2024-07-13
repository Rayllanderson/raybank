package com.rayllanderson.raybank.users.repository;

import com.rayllanderson.raybank.users.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User>findByUsername(String username);
    Optional<User> findByProfilePicture_Key(String key);
    boolean existsByUsername(String username);

    @Query("from users as u where u.id = ?1 ")
    @EntityGraph(attributePaths = "pixKeys")
    Optional<User> findByIdWithPix(String id);
}
