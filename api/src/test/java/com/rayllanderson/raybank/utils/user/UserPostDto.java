package com.rayllanderson.raybank.utils.user;

import com.rayllanderson.raybank.users.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class UserPostDto extends UserDtoModel {

    @Override
    public User toUser() {
        return new ModelMapper().map(this, User.class);
    }
}