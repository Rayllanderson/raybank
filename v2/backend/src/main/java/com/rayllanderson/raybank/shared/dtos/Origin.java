package com.rayllanderson.raybank.shared.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Origin {
    private String identifier;
    private Type type;
    private String referenceTransactionId;
}
