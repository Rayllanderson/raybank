package com.rayllanderson.raybank.dtos.requests.user;

import com.rayllanderson.raybank.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class UserPutDto extends UserDtoModel{
    private String id;

    @Override
    public User toUser() {
        return new ModelMapper().map(this, User.class);
    }
}
