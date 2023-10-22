package com.rayllanderson.raybank.shared.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Destination {
    private String identifier;
    private Type type;

    public boolean isAccount() {
        return Type.ACCOUNT.equals(this.type);
    }
}
