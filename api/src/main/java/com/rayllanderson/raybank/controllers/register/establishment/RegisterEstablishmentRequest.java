package com.rayllanderson.raybank.controllers.register.establishment;

import com.rayllanderson.raybank.services.register.RegisterUserInput;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class RegisterEstablishmentRequest {
    private String name;
    private String username;
    private String password;

    public RegisterUserInput toInput() {
        final var input = new ModelMapper().map(this, RegisterUserInput.class);
        input.setRegisterType(RegisterUserInput.RegisterType.ESTABLISMENT);
        return input;
    }
}
