package com.rayllanderson.raybank.repositories;

import com.rayllanderson.raybank.models.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User>findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByPixKeysKeyOrBankAccountAccountNumber(String pixKey, Integer accountNumber);
    Optional<User> findByPixKeysKeyOrBankAccountAccountNumber(String pixKey, Integer accountNumber);

    @Query("from users as u where u.id = ?1 ")
    @EntityGraph(attributePaths = "pixKeys")
    Optional<User> findByIdWithPix(Long id);
}
