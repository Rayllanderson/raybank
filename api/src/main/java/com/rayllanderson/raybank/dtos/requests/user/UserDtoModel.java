package com.rayllanderson.raybank.dtos.requests.user;

import com.rayllanderson.raybank.models.User;
import lombok.Builder;
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
