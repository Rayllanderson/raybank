package com.rayllanderson.raybank.users.repository;

import com.rayllanderson.raybank.users.model.TempUser;
import com.rayllanderson.raybank.users.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TempUserRepository extends JpaRepository<TempUser, String> {
}
