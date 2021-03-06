package com.rayllanderson.raybank.dtos.responses.user;

import com.rayllanderson.raybank.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPostResponseDto {

    private Long id;
    private String name;
    private String username;

    public static UserPostResponseDto fromUser(User user){
        return new ModelMapper().map(user, UserPostResponseDto.class);
    }
}
