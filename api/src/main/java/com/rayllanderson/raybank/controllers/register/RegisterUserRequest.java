package com.rayllanderson.raybank.controllers.register;

import com.rayllanderson.raybank.services.register.RegisterUserInput;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class RegisterUserRequest {
    private String name;
    private String username;
    private String password;

    public RegisterUserInput toInput() {
        return new ModelMapper().map(this, RegisterUserInput.class);
    }
}
