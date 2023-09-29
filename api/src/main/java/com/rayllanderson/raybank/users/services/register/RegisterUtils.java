package com.rayllanderson.raybank.users.services.register;

import com.rayllanderson.raybank.users.constants.Groups;
import jakarta.ws.rs.core.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.NONE)
public class RegisterUtils {

    public static String getIdFromHeader(final Response response) {
        return response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
    }

    public static List<String> getGroupsByRegisterType(RegisterUserInput.RegisterType registerType) {
        if (RegisterUserInput.RegisterType.ESTABLISHMENT.equals(registerType))
            return List.of(Groups.ESTABLISHMENT.name());
        return List.of(Groups.USER.name());
    }
}
