package com.rayllanderson.raybank.utils;

import com.rayllanderson.raybank.pix.controllers.requests.PixPostDto;
import com.rayllanderson.raybank.pix.model.Pix;

public class PixCreator {

    public static Pix createPixToBeSavedWithoutUser (){
        return Pix.builder()
                .key("any@gmail.com")
                .build();
    }

    public static PixPostDto createPixPixPostDto (){
        return PixPostDto.builder()
                .key("any@gmail.com")
                .build();
    }

    public static Pix createAnotherPixToBeSavedWithoutUser (){
        return Pix.builder()
                .key("whatever@gmail.com")
                .build();
    }
}
