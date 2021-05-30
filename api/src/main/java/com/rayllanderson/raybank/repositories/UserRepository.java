package com.rayllanderson.raybank.repositories;

import com.rayllanderson.raybank.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
