package com.rayllanderson.raybank.dtos.requests.user;

import com.rayllanderson.raybank.models.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.modelmapper.ModelMapper;

@Data
@SuperBuilder
@NoArgsConstructor
public abstract class UserDtoModel {

    private String name;
    private String username;
    private String password;

    public User toUser() {
        return new ModelMapper().map(this, User.class);
    }
}
