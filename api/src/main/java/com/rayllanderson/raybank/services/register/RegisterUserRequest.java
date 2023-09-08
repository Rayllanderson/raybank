package com.rayllanderson.raybank.services.register;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class RegisterUserRequest {

    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    @NotNull
    @Size(min = 3, max = 100)
    private String username;

    @NotNull
    @Size(min = 3, max = 100)
    private String password;

    public RegisterUserInput toInput() {
        return new ModelMapper().map(this, RegisterUserInput.class);
    }
}
