package com.rayllanderson.raybank.pix.service.key.register;

import com.rayllanderson.raybank.pix.model.PixKeyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterPixKeyInput {
    private String key;
    private PixKeyType type;
    private String bankAccountId;
}
