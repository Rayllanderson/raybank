package com.rayllanderson.raybank.users.gateway;

import com.rayllanderson.raybank.users.model.User;

public interface UserGateway {

    User findById(String id);

}
