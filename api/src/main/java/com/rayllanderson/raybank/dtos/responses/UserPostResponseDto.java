package com.rayllanderson.raybank.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPostResponseDto {

    private Long id;
    private String name;
    private String username;
    private String password;
}
