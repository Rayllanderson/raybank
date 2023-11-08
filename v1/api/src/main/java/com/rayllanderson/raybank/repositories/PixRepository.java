package com.rayllanderson.raybank.repositories;

import com.rayllanderson.raybank.models.Pix;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PixRepository extends JpaRepository<Pix, Long> {
    boolean existsByKey(String key);
    boolean existsByKeyAndOwnerId(String key, Long ownerId);
    Integer countByOwnerId(Long userId);
    void deleteByIdAndOwnerId(Long pixId, Long userId);
    Optional<Pix> findByIdAndOwnerId(Long pixId, Long userId);
    List<Pix> findAllByOwnerId(Long ownerId);
}
