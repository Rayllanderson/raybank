package com.rayllanderson.raybank.pix.service.key.find;

import com.rayllanderson.raybank.pix.model.key.PixKeyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class FindPixKeyOutput {
    private String key;
    private PixKeyType type;
    private LocalDateTime createdAt;
}
