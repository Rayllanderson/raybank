package com.rayllanderson.raybank.repositories;

import com.rayllanderson.raybank.models.Pix;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PixRepository extends JpaRepository<Pix, Long> {
    boolean existsByKey(String key);
    boolean existsByKeyAndOwnerId(String key, Long ownerId);
    Integer countByOwnerId(Long userId);
    void deleteByIdAndOwnerId(Long pixId, Long userId);
}
