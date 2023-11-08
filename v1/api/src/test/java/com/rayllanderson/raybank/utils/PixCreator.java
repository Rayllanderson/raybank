package com.rayllanderson.raybank.utils;

import com.rayllanderson.raybank.dtos.requests.pix.PixPostDto;
import com.rayllanderson.raybank.models.Pix;

public class PixCreator {

    public static Pix createPixToBeSavedWithoutUser (){
        return Pix.builder()
                .key("rayllanderson@gmail.com")
                .build();
    }

    public static PixPostDto createPixPixPostDto (){
        return PixPostDto.builder()
                .key("rayllanderson@gmail.com")
                .build();
    }

    public static Pix createAnotherPixToBeSavedWithoutUser (){
        return Pix.builder()
                .key("whatever@gmail.com")
                .build();
    }
}
