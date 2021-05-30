package com.rayllanderson.raybank.repositories;

import com.rayllanderson.raybank.models.Pix;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PixRepository extends JpaRepository<Pix, Long> {
}
