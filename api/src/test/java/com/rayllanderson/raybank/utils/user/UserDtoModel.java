package com.rayllanderson.raybank.utils.user;

import com.rayllanderson.raybank.users.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.modelmapper.ModelMapper;

import jakarta.validation.constraints.Size;

@Data
@SuperBuilder
@NoArgsConstructor
public abstract class UserDtoModel {

    @Size(min = 1, max = 100)
    private String name;
    @Size(min = 3, max = 100)
    private String username;
    @Size(min = 3, max = 100)
    private String password;

    public User toUser() {
        return new ModelMapper().map(this, User.class);
    }
}