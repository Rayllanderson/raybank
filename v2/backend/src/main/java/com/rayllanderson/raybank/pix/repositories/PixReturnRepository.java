package com.rayllanderson.raybank.pix.repositories;

import com.rayllanderson.raybank.pix.model.PixReturn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PixReturnRepository extends JpaRepository<PixReturn, String> {
    List<PixReturn> findAllByOriginId(String pixId);
}
