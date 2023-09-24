package com.rayllanderson.raybank.shared.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Origin {
    private String identifier;
    private Type type;
}
