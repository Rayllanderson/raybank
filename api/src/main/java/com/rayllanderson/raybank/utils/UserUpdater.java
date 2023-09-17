package com.rayllanderson.raybank.utils;

import com.rayllanderson.raybank.dtos.requests.user.UserPutDto;
import com.rayllanderson.raybank.users.model.User;
import org.springframework.beans.BeanUtils;

public class UserUpdater {

    public static void updateNameOrUsername(UserPutDto source, User target) {
        BeanUtils.copyProperties(source, target, "id", "password", "authorities");
    }

    public static void updatePassword(UserPutDto source, User target) {
        BeanUtils.copyProperties(source, target, "id", "name", "username", "authorities");
    }
}
