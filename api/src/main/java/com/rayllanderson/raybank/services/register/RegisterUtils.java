package com.rayllanderson.raybank.services.register;

import com.rayllanderson.raybank.constants.Groups;
import jakarta.ws.rs.core.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.keycloak.representations.idm.CredentialRepresentation;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.NONE)
public class RegisterUtils {

    public static String getIdFromHeader(final Response response) {
        return response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
    }

    public static List<String> getGroupsByRegisterType(RegisterUserInput.RegisterType registerType) {
        if (RegisterUserInput.RegisterType.ESTABLISMENT.equals(registerType))
            return List.of(Groups.ESTABLISMENT.name());
        return List.of(Groups.USER.name());
    }
}
