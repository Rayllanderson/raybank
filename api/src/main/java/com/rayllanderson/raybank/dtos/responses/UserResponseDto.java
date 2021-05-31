package com.rayllanderson.raybank.dtos.responses;

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
public class UserResponseDto {
    private Long id;
    private String name;
    private String username;
    private BankAccountDto bankAccountDto;

    public static UserResponseDto fromUser(User user){
        return new ModelMapper().map(user, UserResponseDto.class);
    }
}
