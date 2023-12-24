package com.rayllanderson.raybank.users.controllers.register.establishment;

import com.rayllanderson.raybank.users.services.register.RegisterUserInput;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class RegisterEstablishmentRequest {
    @NotBlank
    @Size(max = 100)
    private String name;
    @NotBlank
    @Size(max = 100)
    private String username;
    @NotBlank
    @Size(min = 3, max = 20)
    private String password;

    public RegisterUserInput toInput() {
        final var input = new ModelMapper().map(this, RegisterUserInput.class);
        input.setRegisterType(RegisterUserInput.RegisterType.ESTABLISHMENT);
        return input;
    }
}
