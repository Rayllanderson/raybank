package com.rayllanderson.raybank.users.gateway;

import com.rayllanderson.raybank.users.model.User;

public interface UserGateway {
    void save(User user);
    User findById(String id);
    User findByImageKey(String imageKey);

}
