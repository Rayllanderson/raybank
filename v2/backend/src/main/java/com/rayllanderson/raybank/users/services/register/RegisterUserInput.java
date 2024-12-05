package com.rayllanderson.raybank.users.services.register;

import com.rayllanderson.raybank.users.model.User;
import lombok.Getter;
import lombok.Setter;
import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class RegisterUserInput {
    private String id;
    private String name;
    private String username;
    private String email;
    private RegisterType registerType;

    public User toModel() {
        return new ModelMapper().map(this, User.class);
    }

    public static RegisterUserInput from(final UserRepresentation userRepresentation) {
        final var u = new RegisterUserInput();
        u.id = userRepresentation.getId();
        u.email = userRepresentation.getEmail();
        u.username = userRepresentation.getUsername();
        u.registerType = RegisterType.ESTABLISHMENT;
        u.name = userRepresentation.getFirstName().concat(" ").concat(userRepresentation.getLastName());
        return u;
    }

    public enum RegisterType {
        USER, ESTABLISHMENT;

        public static RegisterType from(String s) {
            try {
                return RegisterType.valueOf(s.toUpperCase());
            } catch (Exception e) {
                return USER;
            }
        }
    }

}
