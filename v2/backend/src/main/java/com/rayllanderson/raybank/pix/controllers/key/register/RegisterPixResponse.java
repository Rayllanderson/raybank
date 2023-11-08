package com.rayllanderson.raybank.pix.controllers.key.register;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterPixResponse {
    private String key;
    private String type;

    public static RegisterPixResponse from(String key, String type){
        return new RegisterPixResponse(key, type);
    }
}
