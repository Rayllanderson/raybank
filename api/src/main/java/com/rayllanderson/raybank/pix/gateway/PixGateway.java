package com.rayllanderson.raybank.pix.gateway;

import com.rayllanderson.raybank.pix.model.PixLimit;
import com.rayllanderson.raybank.pix.model.key.PixKey;

import java.util.List;

public interface PixGateway {

    void save(PixKey pix);

    void save(PixLimit pixLimit);

    PixLimit findLimitByAccountId(String accountId);

    boolean existsByKey(final String key);

    void deleteKeyById(final String key);

    List<PixKey> findAllKeysByAccountId(String accountId);

    PixKey findByKey(String key);

    int countKeysByAccountId(String accountId);
}
