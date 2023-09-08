package com.rayllanderson.raybank.services.register;

import com.rayllanderson.raybank.models.User;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class RegisterUserInput {
    private String name;
    private String username;
    private String password;

    public User toModel() {
        return new ModelMapper().map(this, User.class);
    }
}
