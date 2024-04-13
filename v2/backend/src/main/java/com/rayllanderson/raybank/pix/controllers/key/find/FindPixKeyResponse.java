package com.rayllanderson.raybank.pix.controllers.key.find;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FindPixKeyResponse {
    private String key;
    private String type;
    private LocalDateTime createdAt;
}
