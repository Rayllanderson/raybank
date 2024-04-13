package com.rayllanderson.raybank.utils;

import com.rayllanderson.raybank.dtos.requests.user.UserPostDto;
import com.rayllanderson.raybank.dtos.requests.user.UserPutDto;
import com.rayllanderson.raybank.dtos.responses.user.UserPostResponseDto;
import com.rayllanderson.raybank.models.User;

public class UserCreator {

    /**
     * @return username("joao").password("123").name("João")
     */
    public static User createUserToBeSaved() {
        User user = new User();
        user.setName("João");
        user.setUsername("joao");
        user.setPassword("123");
        return user;
    }

    /**
     * @return username("ray").password("123").name("Ray")
     */
    public static User createUserToBeSavedWithCollectionsNonNull() {
        User user = new User();
        user.setUsername("ray");
        user.setPassword("123");
        user.setName("Ray");
        return user;
    }

    /**
     * @return username("ray").password("123").name("Ray")
     */
    public static User createAnotherUserToBeSavedWithCollectionsNonNull() {
        User user = new User();
        user.setUsername("teste");
        user.setPassword("123");
        user.setName("Whatever there");
        return user;
    }

    public static User createUserSavedWithAccount() {
        User user = new User();
        user.setId(50L);
        user.setUsername("teste");
        user.setPassword("123");
        user.setName("Whatever there");
        user.setBankAccount(BankAccountCreator.createBankAccountSaved());
        return user;
    }

    public static User createAnotherUserSavedWithAccount() {
        User user = new User();
        user.setUsername("joao2");
        user.setPassword("123");
        user.setName("Whatever there2");
        user.setBankAccount(BankAccountCreator.createAnotherBankAccountSaved());
        return user;
    }


    /**
     * @return username("joao").password("123").name("João")
     */
    public static UserPostDto createUserPostToBeSaved() {
        return UserPostDto.builder().username("joao").password("123").name("João").build();
    }

    public static UserPostDto createUserToLogIn() {
        return UserPostDto.builder().username("login").password("123").name("Login").build();
    }

    public static UserPostDto createAnotherUserToLogIn() {
        return UserPostDto.builder().username("login2").password("123").name("Login two").build();
    }

    /**
     * @return username("ray").password("123").name("Ray").build();
     */
    public static UserPostDto createAnotherUserToBeSaved() {
        return UserPostDto.builder().username("ray").password("123").name("Ray").build();
    }

    /**
     * @return id(1L).username("joao").password("123").name("João")
     */
    public static User createUserWithId(){
        return User.builder().id(1L).username("joao").password("123").name("João").build();
    }

    public static User createUserAnotherWithId(){
        return User.builder().id(2L).username("ray").password("123").name("Ray").build();
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
