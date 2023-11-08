package com.rayllanderson.raybank.dtos.responses.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rayllanderson.raybank.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserJwtResponse {
    private String username;
    private String name;
    private String token;

    public static UserJwtResponse create(User user, String token){
        UserJwtResponse userDetails = create(user);
        userDetails.setToken(token);
        return userDetails;
    }

    public static UserJwtResponse create(User user){
        return new ModelMapper().map(user, UserJwtResponse.class);
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper m = new ObjectMapper();
        return m.writeValueAsString(this);
    }
}
