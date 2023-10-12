package com.rayllanderson.raybank.pix.controllers.key.register;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterPixKeyRequest {

    private String key;
    @NotNull
    private String type;
}
