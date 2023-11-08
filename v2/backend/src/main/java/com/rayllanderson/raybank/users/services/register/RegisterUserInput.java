package com.rayllanderson.raybank.users.services.register;

import com.rayllanderson.raybank.users.model.User;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class RegisterUserInput {
    private String name;
    private String username;
    private String password;
    private RegisterType registerType;

    public User toModel() {
        return new ModelMapper().map(this, User.class);
    }

    public enum RegisterType {
        USER, ESTABLISHMENT
    }

}
