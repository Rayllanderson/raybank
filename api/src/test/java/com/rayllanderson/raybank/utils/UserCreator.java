package com.rayllanderson.raybank.utils;

import com.rayllanderson.raybank.dtos.requests.user.UserPostDto;
import com.rayllanderson.raybank.dtos.requests.user.UserPutDto;
import com.rayllanderson.raybank.dtos.responses.UserPostResponseDto;
import com.rayllanderson.raybank.models.User;

public class UserCreator {

    /**
     * @return username("joao").password("123").name("João")
     */
    public static User createUserToBeSaved() {
        return User.builder().username("joao").password("123").name("João").build();
    }

    /**
     * @return username("joao").password("123").name("João")
     */
    public static UserPostDto createUserPostToBeSaved() {
        return UserPostDto.builder().username("joao").password("123").name("João").build();
    }

    /**
     * @return id(1L).username("joao").password("123").name("João")
     */
    public static User createUserWithId(){
        return User.builder().id(1L).username("joao").password("123").name("João").build();
    }

    /**
     * @return id(1L).username("joao").password("123").name("João")
     */
    public static UserPostResponseDto createUserPostResponseDto(){
        return UserPostResponseDto.builder().id(1L).username("joao").name("João").build();
    }

    /**
     * @return UserPutDto with id 1
     */
    public static UserPutDto createUserPutDto(){
        return UserPutDto.builder().id(1L).username("joao").name("João").build();
    }

}
