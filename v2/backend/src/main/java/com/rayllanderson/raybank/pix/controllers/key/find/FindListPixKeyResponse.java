package com.rayllanderson.raybank.pix.controllers.key.find;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class FindListPixKeyResponse {
    private int count;
    private List<FindPixKeyResponse> keys;

    public static FindListPixKeyResponse from(List<FindPixKeyResponse> keys) {
        return new FindListPixKeyResponse(keys.size(), keys);
    }
}
