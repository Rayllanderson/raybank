package com.rayllanderson.raybank.repositories;

import com.rayllanderson.raybank.models.Pix;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PixRepository extends JpaRepository<Pix, Long> {
    boolean existsByKey(String key);
    boolean existsByKeyAndOwnerId(String key, String ownerId);
    Integer countByOwnerId(String userId);
    void deleteByIdAndOwnerId(Long pixId, String userId);
    Optional<Pix> findByIdAndOwnerId(Long pixId, String userId);
    List<Pix> findAllByOwnerId(String ownerId);
}
