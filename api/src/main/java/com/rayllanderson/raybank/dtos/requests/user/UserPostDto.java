package com.rayllanderson.raybank.dtos.requests.user;

import com.rayllanderson.raybank.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPostDto {

    private String name;
    private String username;
    private String password;

    public User toUser(){
        return new ModelMapper().map(this, User.class);
    }
}
