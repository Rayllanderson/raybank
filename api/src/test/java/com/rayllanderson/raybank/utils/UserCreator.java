package com.rayllanderson.raybank.utils;

import com.rayllanderson.raybank.dtos.requests.user.UserPostDto;
import com.rayllanderson.raybank.dtos.responses.UserPostResponseDto;
import com.rayllanderson.raybank.models.User;

public class UserCreator {

    public static UserPostDto createUserToBeSaved() {
        return UserPostDto.builder().username("joao").password("123").name("João").build();
    }

    public static User createUserWithId(){
        return User.builder().id(1L).username("joao").password("123").name("João").build();
    }

    public static UserPostResponseDto createUserPostResponseDto(){
        return UserPostResponseDto.builder().id(1L).username("joao").password("123").name("João").build();
    }

}
