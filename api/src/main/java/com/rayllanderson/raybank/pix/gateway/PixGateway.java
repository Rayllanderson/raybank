package com.rayllanderson.raybank.pix.gateway;

import com.rayllanderson.raybank.pix.model.Pix;
import com.rayllanderson.raybank.pix.model.PixLimit;
import com.rayllanderson.raybank.pix.model.PixQrCode;
import com.rayllanderson.raybank.pix.model.key.PixKey;

import java.util.List;

public interface PixGateway {

    void save(Pix pix);

    void save(PixKey pixKey);

    void save(PixLimit pixLimit);

    void save(PixQrCode pixQrCode);

    PixLimit findLimitByAccountId(String accountId);

    boolean existsByKey(final String key);

    boolean existsLimitByAccountId(final String accountId);

    void deleteKeyById(final String key);

    List<PixKey> findAllKeysByAccountId(String accountId);

    PixKey findByKey(String key);

    PixQrCode findQrCodeByQrCode(String qrCode);

    PixKey findKeyByAccountId(String accountId);

    int countKeysByAccountId(String accountId);
}
