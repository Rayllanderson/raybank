package com.rayllanderson.raybank.pix.repositories;

import com.rayllanderson.raybank.pix.model.Pix;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PixRepository extends JpaRepository<Pix, String> {
}
