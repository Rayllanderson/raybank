package com.rayllanderson.raybank.pix.gateway;

import com.rayllanderson.raybank.pix.model.key.PixKey;

import java.util.List;

public interface PixGateway {

    void save(PixKey pix);

    boolean existsByKey(final String key);

    List<PixKey> findAllKeysByAccountId(String accountId);

    PixKey findByKey(String key);

    int countKeysByAccountId(String accountId);
}
